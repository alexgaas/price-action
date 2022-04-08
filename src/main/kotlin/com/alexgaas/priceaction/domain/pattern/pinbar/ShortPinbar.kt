package com.alexgaas.priceaction.domain.pattern.pinbar

import com.alexgaas.priceaction.domain.pattern.Bar

const val SHORT_PINBAR_TILE_LENGTH_METRIC = 1.65

class ShortPinbarTileDown(val barList: List<Bar>) {
    init {
        // we setup tile as at 65% of candlestick
        require(
            barList.last().tileDown >=
                (barList.last().body + barList.last().tileUp) * SHORT_PINBAR_TILE_LENGTH_METRIC.toBigDecimal()
        ) {
            """
                 Tile is not long enough to identify candlestick as pinbar.
                 Height of tile must 65% higher than rest of candlestick.
                 Tile is [${barList.last().tileDown}], rest of body is 
                 [${ (barList.last().body + barList.last().tileUp)}].
            """.trimIndent()
        }
        require(
            barList.last().tileDown >
                barList.last().tileUp * PINBAR_BETWEEN_TILES_LENGTH_METRIC.toBigDecimal()
        ) {
            """
                 Tile is not long enough to identify candlestick as pinbar.
                 Height of tile must higher than tile up of candlestick.
                 Tile is [${barList.last().tileDown}], rest of body is 
                 [${barList.last().tileUp}].
            """.trimIndent()
        }
    }

    val pinbarInfo: Bar
        get() = barList.last()
}

class ShortPinbarTileUp(val barList: List<Bar>) {
    init {
        // we setup tile as at 65% of candlestick
        require(
            barList.last().tileUp >=
                (barList.last().body + barList.last().tileDown) * SHORT_PINBAR_TILE_LENGTH_METRIC.toBigDecimal()
        ) {
            """
                 Tile is not long enough to identify candlestick as pinbar.
                 Height of tile must 65% higher than rest of candlestick.
                 Tile is [${barList.last().tileUp}], rest of body is 
                 [${ (barList.last().body + barList.last().tileDown)}].
            """.trimIndent()
        }
        require(
            barList.last().tileUp >
                barList.last().tileDown * PINBAR_BETWEEN_TILES_LENGTH_METRIC.toBigDecimal()
        ) {
            """
                 Tile is not long enough to identify candlestick as pinbar.
                 Height of tile must higher than tile down of candlestick.
                 Tile is [${barList.last().tileUp}], rest of body is 
                 [${barList.last().tileDown}].
            """.trimIndent()
        }
    }

    val pinbarInfo: Bar
        get() = barList.last()
}
