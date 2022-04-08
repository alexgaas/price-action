package com.alexgaas.priceaction.domain.models.market

import com.alexgaas.priceaction.domain.models.market.enums.OrderPermissions
import com.alexgaas.priceaction.domain.models.market.enums.OrderType
import java.time.ZoneId

data class ExchangeInfo(
    val timezone: ZoneId,
    val serverTime: Long,

    // we ignore filters at this moment of time
    // val rateLimits: List<...> = listOf()

    // we ignore filters at this moment of time
    // val exchangeFilters: List<...> = listOf()

    val symbols: List<ExchangeInfoSymbol>
)

data class ExchangeInfoSymbol(
    val symbol: String,
    val status: String,
    val baseAsset: String,
    val baseAssetPrecision: Int,
    val quoteAsset: String,
    val quoteAssetPrecision: Int,
    val baseCommissionPrecision: Int,
    val quoteCommissionPrecision: Int,
    val orderTypes: List<OrderType>,
    val icebergAllowed: Boolean,
    val ocoAllowed: Boolean,
    val quoteOrderQtyMarketAllowed: Boolean,
    val isSpotTradingAllowed: Boolean,
    val isMarginTradingAllowed: Boolean,

    // we ignore filters at this moment of time
    // val filters: List<...> = listOf()

    val permissions: List<OrderPermissions>
)
