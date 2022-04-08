package com.alexgaas.priceaction.api

import com.alexgaas.priceaction.domain.models.market.CandlestickSetup
import com.alexgaas.priceaction.domain.pattern.Pattern
import com.alexgaas.priceaction.service.PatternService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PatternController(
    val patternService: PatternService
) {
    @PostMapping("/identify-configured-patterns")
    suspend fun setConfiguredMarketData(): List<Pattern?> {
        return patternService.identifyConfiguredPattern()
    }

    @PostMapping("/identify-patterns")
    suspend fun setMarketData(
        @RequestBody candlestickSetup: CandlestickSetup
    ): List<Pattern?> {
        return patternService.identifyPattern(candlestickSetup)
    }
}
