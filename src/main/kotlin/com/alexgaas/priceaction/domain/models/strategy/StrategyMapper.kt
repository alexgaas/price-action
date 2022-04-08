package com.alexgaas.priceaction.domain.models.strategy

import com.alexgaas.priceaction.domain.dto.StrategyDTO
import org.bson.types.ObjectId
import java.time.Instant

object StrategyMapper {
    fun toDTO(strategy: Strategy): StrategyDTO {
        return StrategyDTO(
            id = ObjectId(),
            sequenceId = strategy.sequenceId,
            latest = strategy.latest,
            timestamp = strategy.timestamp.toEpochMilli(),
            symbol = strategy.symbol,
            interval = strategy.interval,
            signalType = strategy.signalType,
            patternType = strategy.patternType,
            enterPrice = strategy.enterPrice,
            exitPrice = strategy.exitPrice,
            currentStopLimitPrice = strategy.currentStopLimitPrice
        )
    }

    fun toDomain(strategyDTO: StrategyDTO): Strategy {
        return Strategy(
            sequenceId = strategyDTO.sequenceId,
            latest = strategyDTO.latest,
            timestamp = Instant.ofEpochMilli(strategyDTO.timestamp),
            symbol = strategyDTO.symbol,
            interval = strategyDTO.interval,
            signalType = strategyDTO.signalType,
            patternType = strategyDTO.patternType,
            enterPrice = strategyDTO.enterPrice,
            exitPrice = strategyDTO.exitPrice,
            currentStopLimitPrice = strategyDTO.currentStopLimitPrice
        )
    }
}
