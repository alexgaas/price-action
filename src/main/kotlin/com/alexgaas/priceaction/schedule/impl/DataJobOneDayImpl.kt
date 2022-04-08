package com.alexgaas.priceaction.schedule.impl

import com.alexgaas.priceaction.common.logger
import com.alexgaas.priceaction.config.ScheduleConfigurationProperties
import com.alexgaas.priceaction.domain.models.market.CandlestickSetup
import com.alexgaas.priceaction.domain.models.market.enums.Interval
import com.alexgaas.priceaction.schedule.DataJob
import com.alexgaas.priceaction.service.MarketDataService
import com.alexgaas.priceaction.strategy.StrategyService
import kotlinx.coroutines.runBlocking
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@EnableConfigurationProperties(ScheduleConfigurationProperties::class)
class DataJobOneDayImpl(
    val scheduleConfigurationProperties: ScheduleConfigurationProperties,
    val marketDataService: MarketDataService,
    val strategyService: StrategyService
) : Job, DataJob {
    private val log by logger(this::class)

    override fun execute(context: JobExecutionContext?) {
        if (!scheduleConfigurationProperties.scheduleToggle.oneDayEnabled) {
            log.debug("Scheduler to get market data for one day is disabled")
            return
        }

        val setup = CandlestickSetup(
            symbol = scheduleConfigurationProperties.symbol,
            interval = Interval.ONE_DAY,
            limit = 1
        )
        runBlocking {
            saveData(setup)
            initiateStrategy(setup)
        }
    }

    override suspend fun saveData(candlestickSetup: CandlestickSetup) {
        log.info("Setup [${candlestickSetup.interval}] candlestick on [${LocalDateTime.now()}]")
        marketDataService.setupCandlesticks(candlestickSetup)
    }

    override suspend fun initiateStrategy(candlestickSetup: CandlestickSetup) {
        log.debug("Start strategy on setup [${LocalDateTime.now()}]")
        strategyService.observe(candlestickSetup)
    }
}
