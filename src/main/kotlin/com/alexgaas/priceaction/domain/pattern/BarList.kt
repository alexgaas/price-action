package com.alexgaas.priceaction.domain.pattern

import com.alexgaas.priceaction.domain.models.market.Candlestick

fun barList(candlesticks: List<Candlestick>): List<Bar> {
    require(candlesticks.count() > 0)

    val highestTileUp = candlesticks.maxOf {
        it.highPrice
    }
    val lowestTileDown = candlesticks.minOf {
        it.lowPrice
    }

    return candlesticks.map {
        val barColor = if (it.closePrice > it.openPrice) {
            BarColor.GREEN
        } else {
            BarColor.RED
        }
        val barHeight = it.highPrice - it.lowPrice
        val body = (it.openPrice - it.closePrice).abs() / barHeight
        val tileUp = (it.highPrice - it.closePrice) / barHeight
        val tileDown = (it.openPrice - it.lowPrice) / barHeight

        val minInList = if (it.lowPrice !== lowestTileDown) {
            lowestTileDown / it.lowPrice
        } else {
            0.toBigDecimal()
        }
        val maxInList = if (it.highPrice !== highestTileUp) {
            it.highPrice / highestTileUp
        } else {
            1.toBigDecimal()
        }

        Bar(
            body = body,
            tileUp = tileUp,
            tileDown = tileDown,
            minInList = minInList,
            maxInList = maxInList,
            color = barColor
        )
    }
}
