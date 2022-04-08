package com.alexgaas.priceaction.service.impl

import com.alexgaas.priceaction.common.logger
import com.alexgaas.priceaction.config.CandlestickDataConfig
import com.alexgaas.priceaction.domain.dto.CandlestickDTO
import com.alexgaas.priceaction.domain.models.market.CandlestickMapper
import com.alexgaas.priceaction.domain.models.market.CandlestickSetup
import com.alexgaas.priceaction.domain.models.market.CandlestickSetupMapper
import com.alexgaas.priceaction.domain.pattern.Pattern
import com.alexgaas.priceaction.domain.pattern.PatternType
import com.alexgaas.priceaction.domain.pattern.barList
import com.alexgaas.priceaction.domain.pattern.pinbar.LONG_PINBAR_CANDLES_COUNT
import com.alexgaas.priceaction.domain.pattern.pinbar.LongPinbarTileDown
import com.alexgaas.priceaction.domain.pattern.pinbar.LongPinbarTileUp
import com.alexgaas.priceaction.domain.pattern.pinbar.ShortPinbarTileDown
import com.alexgaas.priceaction.domain.pattern.pinbar.ShortPinbarTileUp
import com.alexgaas.priceaction.repo.MarketDataRepository
import com.alexgaas.priceaction.service.PatternService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.time.Instant

@Service
@EnableConfigurationProperties(CandlestickDataConfig::class)
class PatternServiceImpl(
    val candlestickDataConfig: CandlestickDataConfig,
    val marketDataRepository: MarketDataRepository
) : PatternService {

    private val log by logger(this::class)

    override suspend fun identifyPattern(candlestickSetup: CandlestickSetup): List<Pattern?> {
        return marketDataRepository.findBySetupAttributes(candlestickSetup).map {
            identifyLongPinbar(it) ?: identifyShortPinbar(it)
        }
    }

    override suspend fun identifyConfiguredPattern(): List<Pattern?> {
        log.debug("Identify pattern based on pre-configured setup")
        val settings = CandlestickSetupMapper.toDomain(candlestickDataConfig)
        return identifyPattern(settings)
    }

    suspend fun identifyLongPinbar(latestCandlestick: CandlestickDTO): Pattern? {
        val setupLatest = CandlestickSetup(
            symbol = latestCandlestick.symbol,
            interval = latestCandlestick.interval,
            startTime = null,
            endTime = latestCandlestick.closeTime,
            limit = LONG_PINBAR_CANDLES_COUNT
        )
        val latestCandlestickDTOs = marketDataRepository.findBySetupAttributes(setupLatest)
        val latestCandlesticks = latestCandlestickDTOs.map {
            CandlestickMapper.toDomain(it)
        }
        val longPinbarUp = kotlin.runCatching {
            LongPinbarTileUp(barList(latestCandlesticks))
        }.getOrNull()
        val longPinbarDown = kotlin.runCatching {
            LongPinbarTileDown(barList(latestCandlesticks))
        }.getOrNull()

        return if (longPinbarUp != null && longPinbarDown == null) {
            Pattern(
                type = PatternType.LONG_PINBAR_UP,
                label = "Long pinbar up",
                color = longPinbarUp.pinbarInfo.color,
                interval = latestCandlestick.interval,
                symbol = latestCandlestick.symbol,
                datetime = Instant.ofEpochMilli(latestCandlestick.closeTime)
            )
        } else if (longPinbarDown != null && longPinbarUp == null) {
            Pattern(
                type = PatternType.LONG_PINBAR_DOWN,
                label = "Long pinbar down",
                color = longPinbarDown.pinbarInfo.color,
                interval = latestCandlestick.interval,
                symbol = latestCandlestick.symbol,
                datetime = Instant.ofEpochMilli(latestCandlestick.closeTime)
            )
        } else {
            null
        }
    }

    suspend fun identifyShortPinbar(latestCandlestick: CandlestickDTO): Pattern? {
        val latestCandlesticks = listOf(CandlestickMapper.toDomain(latestCandlestick))

        val shortPinbarUp = kotlin.runCatching {
            ShortPinbarTileUp(barList(latestCandlesticks))
        }.getOrNull()
        val shortPinbarDown = kotlin.runCatching {
            ShortPinbarTileDown(barList(latestCandlesticks))
        }.getOrNull()

        return if (shortPinbarUp != null && shortPinbarDown == null) {
            Pattern(
                type = PatternType.SHORT_PINBAR_UP,
                label = "Short pinbar up",
                color = shortPinbarUp.pinbarInfo.color,
                interval = latestCandlestick.interval,
                symbol = latestCandlestick.symbol,
                datetime = Instant.ofEpochMilli(latestCandlestick.closeTime)
            )
        } else if (shortPinbarDown != null && shortPinbarUp == null) {
            Pattern(
                type = PatternType.SHORT_PINBAR_DOWN,
                label = "Short pinbar down",
                color = shortPinbarDown.pinbarInfo.color,
                interval = latestCandlestick.interval,
                symbol = latestCandlestick.symbol,
                datetime = Instant.ofEpochMilli(latestCandlestick.closeTime)
            )
        } else {
            null
        }
    }
}
