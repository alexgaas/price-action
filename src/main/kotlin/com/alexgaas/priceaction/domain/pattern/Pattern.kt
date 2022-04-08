package com.alexgaas.priceaction.domain.pattern

import com.alexgaas.priceaction.domain.models.market.enums.Interval
import java.time.Instant

data class Pattern(
    val type: PatternType,
    val label: String?,
    val color: BarColor,

    // common data
    val interval: Interval,
    val symbol: String,
    val datetime: Instant,
)
