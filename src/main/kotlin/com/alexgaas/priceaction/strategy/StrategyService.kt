package com.alexgaas.priceaction.strategy

import com.alexgaas.priceaction.domain.models.market.CandlestickSetup
import com.alexgaas.priceaction.domain.models.notification.NotificationTarget
import com.alexgaas.priceaction.domain.strategy.enums.SignalType
import org.bson.types.ObjectId

interface StrategyService {
    suspend fun observe(candlestickSetup: CandlestickSetup)
    suspend fun sendSignal(targets: List<NotificationTarget>)
}
