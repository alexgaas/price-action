package com.alexgaas.priceaction.schedule

import com.alexgaas.priceaction.domain.models.market.CandlestickSetup

interface DataJob {
    suspend fun saveData(candlestickSetup: CandlestickSetup)
    suspend fun initiateStrategy(candlestickSetup: CandlestickSetup)
}
