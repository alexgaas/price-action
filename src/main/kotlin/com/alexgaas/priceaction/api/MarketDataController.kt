package com.alexgaas.priceaction.api

import com.alexgaas.priceaction.domain.models.market.Candlestick
import com.alexgaas.priceaction.domain.models.market.CandlestickSetup
import com.alexgaas.priceaction.domain.models.market.ExchangeInfo
import com.alexgaas.priceaction.domain.models.market.ShortCandlestick
import com.alexgaas.priceaction.service.MarketDataService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MarketDataController(
    val marketDataService: MarketDataService
) {
    /*
    Market Data API
     */
    @PostMapping("/setup-configured-candlestick-data")
    suspend fun setConfiguredMarketData() {
        marketDataService.setupConfiguredCandlesticks()
    }

    @PostMapping("/setup-candlestick-data")
    suspend fun setMarketData(
        @RequestBody candlestickSetup: CandlestickSetup
    ) {
        marketDataService.setupCandlesticks(candlestickSetup)
    }

    @PostMapping("/get-candlestick-data")
    suspend fun getMarketData(
        @RequestBody candlestickSetup: CandlestickSetup
    ): List<Candlestick> {
        return marketDataService.getCandlesticks(candlestickSetup)
    }

    @PostMapping("/plot-candlestick-data")
    suspend fun getMarketDataForPlotting(
        @RequestBody candlestickSetup: CandlestickSetup
    ): List<ShortCandlestick> {
        return marketDataService.getShortCandlesticks(candlestickSetup)
    }

    @DeleteMapping("/delete-candlesticks")
    suspend fun deleteMarketData(
        @RequestBody candlestickSetup: CandlestickSetup?
    ) {
        marketDataService.removeCandlesticks(candlestickSetup)
    }

    /*
    Exchange Info API
     */
    @PostMapping("/setup-exchange-info")
    suspend fun postExchangeInfo(
        @RequestParam symbols: List<String>
    ): ExchangeInfo {
        return marketDataService.setupExchangeInfo(symbols)
    }

    @PostMapping("/get-exchange-info")
    suspend fun getExchangeInfo(
        @RequestParam symbols: List<String> = listOf()
    ): List<ExchangeInfo> {
        return marketDataService.getExchangeInfo(symbols)
    }
}
