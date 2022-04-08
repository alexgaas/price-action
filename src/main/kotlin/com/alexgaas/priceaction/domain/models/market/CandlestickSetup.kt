package com.alexgaas.priceaction.domain.models.market

import com.alexgaas.priceaction.domain.models.market.enums.Interval

data class CandlestickSetup(
    val symbol: String,
    val interval: Interval,
    val startTime: Long? = null,
    val endTime: Long? = null,
    val limit: Int?
)
