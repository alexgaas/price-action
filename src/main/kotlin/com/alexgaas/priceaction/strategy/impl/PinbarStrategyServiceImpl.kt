package com.alexgaas.priceaction.strategy.impl

import com.alexgaas.priceaction.common.logger
import com.alexgaas.priceaction.domain.models.market.CandlestickSetup
import com.alexgaas.priceaction.domain.models.notification.NotificationTarget
import com.alexgaas.priceaction.domain.models.notification.enums.MessageType
import com.alexgaas.priceaction.domain.models.notification.enums.NotificationType
import com.alexgaas.priceaction.domain.models.strategy.Strategy
import com.alexgaas.priceaction.domain.models.strategy.StrategyMapper
import com.alexgaas.priceaction.domain.pattern.Pattern
import com.alexgaas.priceaction.domain.pattern.PatternType
import com.alexgaas.priceaction.domain.strategy.enums.SignalType
import com.alexgaas.priceaction.repo.MarketDataRepository
import com.alexgaas.priceaction.repo.StrategyRepository
import com.alexgaas.priceaction.service.NotificationService
import com.alexgaas.priceaction.service.PatternService
import com.alexgaas.priceaction.strategy.StrategyService
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.Instant

@Component
class PinbarStrategyServiceImpl(
    val marketDataRepository: MarketDataRepository,
    val strategyRepository: StrategyRepository,
    val patternService: PatternService,
    val notificationService: NotificationService
) : StrategyService {
    private val log by logger(this::class)

    override suspend fun observe(candlestickSetup: CandlestickSetup) {
        val identifiedPatternsList = patternService.identifyPattern(candlestickSetup)
        val identifiedPattern = identifiedPatternsList.first() ?: run {
            log.debug("Pattern not been identified based on setup [$candlestickSetup]")
            null
        }

        identifiedPattern?.let { pattern ->
            when (pattern.type) {
                PatternType.LONG_PINBAR_UP, PatternType.SHORT_PINBAR_UP -> {
                    exitSignal(candlestickSetup, pattern, SignalType.EXIT_BY_PATTERN, MessageType.PINBAR_UP)?.let {
                        sendSignal(listOf(it))
                    }
                }
                PatternType.LONG_PINBAR_DOWN, PatternType.SHORT_PINBAR_DOWN -> {
                    val targets = mutableListOf<NotificationTarget>()
                    enterOrObserveSignal(candlestickSetup, pattern, SignalType.ENTER_BY_PATTERN, MessageType.PINBAR_DOWN).let {
                        targets.add(it)
                    }
                    // make stop loss on pinbar tile as 60%, can be configured later
                    val stopLossTile = 0.6.toBigDecimal()
                    // since pattern identified we can just get latest
                    val pinbarKlin = marketDataRepository.findBySetupAttributes(candlestickSetup).last()
                    val stopLossPrice = pinbarKlin.closePrice - pinbarKlin.lowPrice * stopLossTile
                    stopLimitSignal(candlestickSetup, stopLossPrice).let {
                        targets.add(it)
                    }
                    when {
                        targets.isNotEmpty() -> {
                            sendSignal(targets)
                        }
                        else -> log.warn(
                            """
                            Both target events for [${MessageType.PINBAR_DOWN}] are empty,
                            signal will not be sent.
                            """.trimIndent()
                        )
                    }
                }
                else -> log.warn("Pattern type [${pattern.type}] is not implemented yet")
            }
        } ?: run {
            strategyRepository.findLatestBySymbolAndInterval(
                symbol = candlestickSetup.symbol,
                interval = candlestickSetup.interval
            )?.let {
                /*
                if candle.color == RED and candle.closePrice > currentStopLimit {
                    send observe signal
                } else if candle.color == RED and currentStopLimit < currentPrice {
                    send exit signal
                }

                if candle.color == GREEN and closePrice > currentStopLimit {
                    setup stop limit as 50% of current candle
                    send observe signal
                } else if candle.color == RED and currentStopLimit < currentPrice {
                    send observe signal
                }
                 */

            }
        }
    }

    private suspend fun stopLimitSignal(
        candlestickSetup: CandlestickSetup,
        stopLimitPrice: BigDecimal
    ): NotificationTarget {
        strategyRepository.findLatestBySymbolAndInterval(
            symbol = candlestickSetup.symbol,
            interval = candlestickSetup.interval
        )?.let { strategyRepository.save(it.copy(currentStopLimitPrice = stopLimitPrice)) }
        return NotificationTarget(
            id = MessageType.STOP_LIMIT,
            symbol = candlestickSetup.symbol,
            stopLimit = stopLimitPrice
        )
    }

    private suspend fun enterOrObserveSignal(
        candlestickSetup: CandlestickSetup,
        pattern: Pattern,
        signal: SignalType,
        messageType: MessageType
    ): NotificationTarget {
        strategyRepository.findLatestBySymbolAndInterval(
            symbol = candlestickSetup.symbol,
            interval = candlestickSetup.interval
        )?.let { strategyRepository.save(it.copy(latest = false)) }
        strategyRepository.save(
            StrategyMapper.toDTO(
                Strategy(
                    sequenceId = null,
                    latest = true,
                    timestamp = Instant.now(),
                    symbol = pattern.symbol,
                    interval = pattern.interval,
                    signalType = signal,
                    patternType = pattern.type,
                    // TODO
                    enterPrice = 0.toBigDecimal(),
                    currentStopLimitPrice = 0.toBigDecimal(),
                    exitPrice = 0.toBigDecimal()
                )
            )
        )
        return NotificationTarget(
            id = messageType,
            symbol = pattern.symbol,
            color = pattern.color,
            interval = pattern.interval
        )
    }

    private suspend fun exitSignal(
        candlestickSetup: CandlestickSetup,
        pattern: Pattern,
        signal: SignalType,
        messageType: MessageType
    ): NotificationTarget? {
        return strategyRepository.findLatestBySymbolAndInterval(
            symbol = candlestickSetup.symbol,
            interval = candlestickSetup.interval
        )?.let {
            strategyRepository.save(it.copy(latest = false))
            strategyRepository.save(
                StrategyMapper.toDTO(
                    Strategy(
                        sequenceId = it.sequenceId,
                        latest = true,
                        timestamp = Instant.now(),
                        symbol = pattern.symbol,
                        interval = pattern.interval,
                        signalType = signal,
                        patternType = pattern.type,
                        // TODO
                        enterPrice = 0.toBigDecimal(),
                        currentStopLimitPrice = 0.toBigDecimal(),
                        exitPrice = 0.toBigDecimal()
                    )
                )
            )
            NotificationTarget(
                id = messageType,
                symbol = pattern.symbol,
                color = pattern.color,
                interval = pattern.interval
            )
        }
    }

    override suspend fun sendSignal(targets: List<NotificationTarget>) {
        log.info("Signal sent for [$targets]")
        notificationService.sendNotification(
            targets,
            NotificationType.TWILLO_WHATSUP_MESSAGE
        )
    }
}
