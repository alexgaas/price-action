package com.alexgaas.priceaction.domain.models.market

import com.alexgaas.priceaction.config.CandlestickDataConfig

object CandlestickSetupMapper {
    fun toDomain(candlestickDataConfig: CandlestickDataConfig): CandlestickSetup {
        return CandlestickSetup(
            symbol = candlestickDataConfig.symbol,
            interval = candlestickDataConfig.interval,
            startTime = candlestickDataConfig.startTime?.toMillis(),
            endTime = candlestickDataConfig.endTime?.toMillis(),
            limit = candlestickDataConfig.limit
        )
    }
}
