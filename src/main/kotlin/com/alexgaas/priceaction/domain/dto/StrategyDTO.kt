package com.alexgaas.priceaction.domain.dto

import com.alexgaas.priceaction.domain.models.market.enums.Interval
import com.alexgaas.priceaction.domain.pattern.PatternType
import com.alexgaas.priceaction.domain.strategy.enums.SignalType
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document("strategy")
data class StrategyDTO(
    @Id
    @JsonSerialize(using = ToStringSerializer::class)
    val id: ObjectId,

    @JsonSerialize(using = ToStringSerializer::class)
    val sequenceId: ObjectId? = null,

    val latest: Boolean,

    val timestamp: Long,
    val symbol: String,
    val interval: Interval,
    val signalType: SignalType,
    val patternType: PatternType? = null,

    val enterPrice: BigDecimal,
    val exitPrice: BigDecimal?,
    val currentStopLimitPrice: BigDecimal?
)
