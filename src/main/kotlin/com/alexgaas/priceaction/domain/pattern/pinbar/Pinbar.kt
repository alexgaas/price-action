package com.alexgaas.priceaction.domain.pattern.pinbar

import com.alexgaas.priceaction.domain.pattern.Bar

const val LONG_PINBAR_CANDLES_COUNT = 3
const val LONG_PINBAR_TILE_LENGTH_METRIC = 1.75
const val PINBAR_BETWEEN_TILES_LENGTH_METRIC = 1.5

class LongPinbarTileDown(val barList: List<Bar>) {
    init {
        // 1 - we setup at least [PINBAR_CANDLES_COUNT] candlesticks to identify pinbar
        require(barList.count() >= LONG_PINBAR_CANDLES_COUNT) {
            """
                Incoming list of candlestick must be $LONG_PINBAR_CANDLES_COUNT or more to identify pinbar pattern.
                Current number of candlesticks is [${barList.count()}]
            """.trimIndent()
        }
        // 2 - we setup tile as at 75% of candlestick body / tile
        require(
            barList.last().tileDown >=
                barList.last().body * LONG_PINBAR_TILE_LENGTH_METRIC.toBigDecimal()
        ) {
            """
                 Tile is not long enough to identify candlestick as pinbar.
                 Height of tile must 75% higher than body of candlestick.
                 Tile is [${barList.last().tileDown}], rest of body is 
                 [${barList.last().body}].
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
        // 3 - we setup tile down more than for other candlesticks around
        require(
            barList.last().minInList == 0.toBigDecimal()
        ) {
            """
                Candlestick does not have tallest tile, so it could not be identified as pinbar.
                Current value of [minInList] is [${barList.last().minInList}].
            """.trimIndent()
        }
    }

    val pinbarInfo: Bar
        get() = barList.last()
}

class LongPinbarTileUp(val barList: List<Bar>) {
    init {
        // 1 - we setup at least [PINBAR_CANDLES_COUNT] candlesticks to identify pinbar
        require(barList.count() >= LONG_PINBAR_CANDLES_COUNT) {
            """
                Incoming list of candlestick must be $LONG_PINBAR_CANDLES_COUNT or more to identify pinbar pattern.
                Current number of candlesticks is [${barList.count()}]
            """.trimIndent()
        }
        // 2 - we setup tile as at 75% of candlestick
        require(
            barList.last().tileUp >=
                barList.last().body * LONG_PINBAR_TILE_LENGTH_METRIC.toBigDecimal()
        ) {
            """
                 Tile is not long enough to identify candlestick as pinbar.
                 Height of tile must 75% higher than body of candlestick.
                 Tile is [${barList.last().tileUp}], rest of body is 
                 [${barList.last().body}].
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
        // 3 - we setup tile down more than for other candlesticks around
        require(
            barList.last().maxInList == 1.toBigDecimal()
        ) {
            """
                Candlestick does not have tallest tile, so it could not be identified as pinbar.
                Current value of [maxInList] is [${barList.last().maxInList}].
            """.trimIndent()
        }
    }

    val pinbarInfo: Bar
        get() = barList.last()
}
