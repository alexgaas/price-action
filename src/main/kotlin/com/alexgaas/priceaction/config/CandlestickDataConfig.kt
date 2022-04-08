package com.alexgaas.priceaction.config

import com.alexgaas.priceaction.domain.models.market.enums.Interval
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConfigurationProperties(prefix = "candlestick-data-setup")
@ConstructorBinding
data class CandlestickDataConfig(
    val symbol: String,
    val interval: Interval,
    val startTime: Duration?,
    val endTime: Duration?,
    val limit: Int?
)
