package com.alexgaas.priceaction.domain.models.strategy

import com.alexgaas.priceaction.domain.models.market.enums.Interval
import com.alexgaas.priceaction.domain.pattern.PatternType
import com.alexgaas.priceaction.domain.strategy.enums.SignalType
import org.bson.types.ObjectId
import java.math.BigDecimal
import java.time.Instant

data class Strategy(
    val sequenceId: ObjectId?,
    val latest: Boolean,
    val timestamp: Instant,
    val symbol: String,
    val interval: Interval,
    val signalType: SignalType,
    val patternType: PatternType?,
    val enterPrice: BigDecimal,
    val exitPrice: BigDecimal? = null,
    val currentStopLimitPrice: BigDecimal? = null
)
