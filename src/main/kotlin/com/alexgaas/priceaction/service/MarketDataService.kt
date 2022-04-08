package com.alexgaas.priceaction.service

import com.alexgaas.priceaction.domain.models.market.Candlestick
import com.alexgaas.priceaction.domain.models.market.CandlestickSetup
import com.alexgaas.priceaction.domain.models.market.ExchangeInfo
import com.alexgaas.priceaction.domain.models.market.ShortCandlestick

interface MarketDataService {
    // setup market data
    suspend fun setupConfiguredCandlesticks(): List<Candlestick>
    suspend fun setupCandlesticks(candlestickSetup: CandlestickSetup): List<Candlestick>
    // get market data
    suspend fun getCandlesticks(candlestickSetup: CandlestickSetup): List<Candlestick>
    // get candlesticks for plotting
    suspend fun getShortCandlesticks(candlestickSetup: CandlestickSetup): List<ShortCandlestick>
    // remove candlesticks
    suspend fun removeCandlesticks(candlestickSetup: CandlestickSetup?)

    // setup exchange info
    suspend fun setupExchangeInfo(symbols: List<String>): ExchangeInfo
    // get exchange info
    suspend fun getExchangeInfo(symbols: List<String>): List<ExchangeInfo>
}
