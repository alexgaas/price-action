package com.alexgaas.priceaction.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("binance.api")
@ConstructorBinding
data class BinanceConfig(
    val apiKey: String,
    val host: String,
    val endpoints: Endpoints
) {
    data class Endpoints(
        val general: General,
        val tradeData: TradeData
    )

    data class General(
        val testConnectivity: String,
        val checkServerTime: String
    )

    data class TradeData(
        val candlestickData: String,
        val exchangeInformation: String
    )
}
