package com.alexgaas.priceaction.unit.pattern

import com.alexgaas.priceaction.domain.pattern.Bar
import com.alexgaas.priceaction.domain.pattern.BarColor
import com.alexgaas.priceaction.domain.pattern.pinbar.LongPinbarTileDown
import com.alexgaas.priceaction.domain.pattern.pinbar.LongPinbarTileUp
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PinbarTest {
    @Test
    fun testPinbarDown() {
        val barList = mutableListOf<Bar>()
        barList.add(
            Bar(
                body = 0.2.toBigDecimal(),
                tileUp = 0.1.toBigDecimal(),
                tileDown = 0.1.toBigDecimal(),
                color = BarColor.RED,
                maxInList = 1.toBigDecimal(),
                minInList = 0.4.toBigDecimal()
            )
        )

        barList.add(
            Bar(
                body = 0.2.toBigDecimal(),
                tileUp = 0.1.toBigDecimal(),
                tileDown = 0.1.toBigDecimal(),
                color = BarColor.GREEN,
                maxInList = 0.7.toBigDecimal(),
                minInList = 0.3.toBigDecimal()
            )
        )

        barList.add(
            Bar(
                body = 0.1.toBigDecimal(),
                tileUp = 0.05.toBigDecimal(),
                tileDown = 0.4.toBigDecimal(),
                color = BarColor.RED,
                maxInList = 0.5.toBigDecimal(),
                minInList = 0.toBigDecimal()
            )
        )

        Assertions.assertNotNull(LongPinbarTileDown(barList))
    }

    @Test
    fun testPinbarUp() {
        val barList = mutableListOf<Bar>()
        barList.add(
            Bar(
                body = 0.2.toBigDecimal(),
                tileUp = 0.1.toBigDecimal(),
                tileDown = 0.1.toBigDecimal(),
                color = BarColor.RED,
                maxInList = 1.toBigDecimal(),
                minInList = 0.4.toBigDecimal()
            )
        )

        barList.add(
            Bar(
                body = 0.2.toBigDecimal(),
                tileUp = 0.1.toBigDecimal(),
                tileDown = 0.1.toBigDecimal(),
                color = BarColor.GREEN,
                maxInList = 0.7.toBigDecimal(),
                minInList = 0.3.toBigDecimal()
            )
        )

        barList.add(
            Bar(
                body = 0.1.toBigDecimal(),
                tileUp = 0.4.toBigDecimal(),
                tileDown = 0.05.toBigDecimal(),
                color = BarColor.GREEN,
                maxInList = 1.toBigDecimal(),
                minInList = 0.4.toBigDecimal()
            )
        )

        Assertions.assertNotNull(LongPinbarTileUp(barList))
    }
}
