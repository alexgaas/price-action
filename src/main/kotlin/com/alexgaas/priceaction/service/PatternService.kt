package com.alexgaas.priceaction.service

import com.alexgaas.priceaction.domain.models.market.CandlestickSetup
import com.alexgaas.priceaction.domain.pattern.Pattern

interface PatternService {
    suspend fun identifyPattern(candlestickSetup: CandlestickSetup): List<Pattern?>
    suspend fun identifyConfiguredPattern(): List<Pattern?>
}
