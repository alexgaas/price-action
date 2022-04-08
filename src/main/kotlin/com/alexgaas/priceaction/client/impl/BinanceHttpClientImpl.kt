package com.alexgaas.priceaction.client.impl

import com.alexgaas.priceaction.client.BinanceHttpClient
import com.alexgaas.priceaction.config.BinanceConfig
import com.alexgaas.priceaction.domain.models.general.CheckServerTimeResponse
import com.alexgaas.priceaction.domain.models.market.Candlestick
import com.alexgaas.priceaction.domain.models.market.CandlestickSetup
import com.alexgaas.priceaction.domain.models.market.ExchangeInfo
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodilessEntity
import org.springframework.web.reactive.function.client.awaitBody

@Service
@Configuration
@EnableConfigurationProperties(BinanceConfig::class)
class BinanceHttpClientImpl(
    val binanceConfig: BinanceConfig,
    webClientBuilder: WebClient.Builder
) : BinanceHttpClient {
    var webClient: WebClient = webClientBuilder
        .baseUrl(binanceConfig.host)
        .build()

    override suspend fun testConnectivity(): Boolean {
        return runCatching {
            webClient.get()
                .uri { builder ->
                    builder
                        .path(binanceConfig.endpoints.general.testConnectivity)
                        .build()
                }
                .header("X-MBX-APIKEY", binanceConfig.apiKey)
                .retrieve()
                .awaitBodilessEntity()
            true
        }.getOrDefault(false)
    }

    override suspend fun checkServerTime(): CheckServerTimeResponse {
        return webClient.get()
            .uri { builder ->
                builder
                    .path(binanceConfig.endpoints.general.checkServerTime)
                    .build()
            }
            .header("X-MBX-APIKEY", binanceConfig.apiKey)
            .retrieve()
            .awaitBody()
    }

    override suspend fun getCandlestickData(candlestickSetup: CandlestickSetup): List<Candlestick> {
        return webClient.get()
            .uri { builder ->
                builder
                    .path(binanceConfig.endpoints.tradeData.candlestickData).apply {
                        queryParam("symbol", candlestickSetup.symbol)
                        queryParam("interval", candlestickSetup.interval.code)
                        if (candlestickSetup.startTime != null) queryParam("startTime", candlestickSetup.startTime)
                        if (candlestickSetup.endTime != null) queryParam("endTime", candlestickSetup.endTime)
                        if (candlestickSetup.limit != null) queryParam("limit", candlestickSetup.limit)
                    }.build()
            }
            .header("X-MBX-APIKEY", binanceConfig.apiKey)
            .retrieve()
            .awaitBody()
    }

    override suspend fun getExchangeInfo(symbols: List<String>): ExchangeInfo {
        return webClient.get()
            .uri { builder ->
                builder
                    .path(binanceConfig.endpoints.tradeData.exchangeInformation).apply {
                        queryParam("symbols", symbols)
                    }.build()
            }
            .header("X-MBX-APIKEY", binanceConfig.apiKey)
            .retrieve()
            .awaitBody()
    }
}
