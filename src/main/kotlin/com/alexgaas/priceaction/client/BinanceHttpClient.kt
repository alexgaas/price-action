package com.alexgaas.priceaction.client

import com.alexgaas.priceaction.domain.models.general.CheckServerTimeResponse
import com.alexgaas.priceaction.domain.models.market.Candlestick
import com.alexgaas.priceaction.domain.models.market.CandlestickSetup
import com.alexgaas.priceaction.domain.models.market.ExchangeInfo

interface BinanceHttpClient {
    // general endpoints
    suspend fun testConnectivity(): Boolean
    suspend fun checkServerTime(): CheckServerTimeResponse

    // market data endpoints
    suspend fun getCandlestickData(candlestickSetup: CandlestickSetup): List<Candlestick>
    suspend fun getExchangeInfo(symbols: List<String>): ExchangeInfo
}
