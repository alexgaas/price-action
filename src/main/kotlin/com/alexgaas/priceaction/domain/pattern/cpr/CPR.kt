package com.alexgaas.priceaction.domain.pattern.cpr

import com.alexgaas.priceaction.domain.pattern.Bar
import com.alexgaas.priceaction.domain.pattern.BarColor

const val TILE_LENGTH_METRIC = 1.5
const val BODY_LENGTH_METRIC = 1.15

class CPRUp(val barList: List<Bar>) {
    init {
        // 1 - we setup at least 2 candlesticks to identify CPR
        require(barList.count() != 2) {
            """
                Incoming list of candlestick must be exactly 2 to identify CPR pattern.
                Current number of candlesticks is [${barList.count()}]
            """.trimIndent()
        }
        // 2 - CPR up going as first candle as green and second as red
        require(barList.first().color != BarColor.GREEN) {
            """
                First candlestick must be [${BarColor.GREEN}] to identify CPR pattern
            """.trimIndent()
        }
        require(barList.last().color != BarColor.RED) {
            """
                First candlestick must be [${BarColor.RED}] to identify CPR pattern
            """.trimIndent()
        }
        // 3 - identify CPR up
        require(barList.last().tileUp >= barList.first().tileUp * TILE_LENGTH_METRIC.toBigDecimal()) {
            """
                Second candlestick tile up must be higher then first at least [$TILE_LENGTH_METRIC] times 
                to identify CPR pattern. 
            """.trimIndent()
        }
        require(
            (barList.first().body + barList.first().tileDown) >=
                (barList.last().body + barList.last().tileDown) * BODY_LENGTH_METRIC.toBigDecimal()
        ) {
            """
                Second candlestick body / tile down must be lower then first at least [$BODY_LENGTH_METRIC] times 
                to identify CPR pattern.
            """.trimIndent()
        }
    }
}

class CPRDown(val barList: List<Bar>) {
    init {
        // 1 - we setup at least 2 candlesticks to identify CPR
        require(barList.count() != 2) {
            """
                Incoming list of candlestick must be exactly 2 to identify CPR pattern.
                Current number of candlesticks is [${barList.count()}]
            """.trimIndent()
        }
        // 2 - CPR down going as first candle as red and second as green
        require(barList.first().color != BarColor.RED) {
            """
                First candlestick must be [${BarColor.RED}] to identify CPR pattern
            """.trimIndent()
        }
        require(barList.last().color != BarColor.GREEN) {
            """
                First candlestick must be [${BarColor.GREEN}] to identify CPR pattern
            """.trimIndent()
        }
        // 3 - identify CPR up
        require(barList.last().tileDown >= barList.first().tileDown * TILE_LENGTH_METRIC.toBigDecimal()) {
            """
                Second candlestick tile down must be lower then first at least [$TILE_LENGTH_METRIC] times 
                to identify CPR pattern. 
            """.trimIndent()
        }
        require(
            (barList.first().body + barList.first().tileUp) >=
                (barList.last().body + barList.last().tileUp) * BODY_LENGTH_METRIC.toBigDecimal()
        ) {
            """
                Second candlestick body / tile down must be lower then first at least [$BODY_LENGTH_METRIC] times 
                to identify CPR pattern.
            """.trimIndent()
        }
    }
}
