package com.alexgaas.priceaction.domain.pattern

import java.math.BigDecimal

data class Bar(
    // body coefficient - between 0 and 1
    val body: BigDecimal,

    // tile up coefficient - between 0 and 1
    val tileUp: BigDecimal,

    // tile down coefficient - between 0 and 1
    val tileDown: BigDecimal,

    // bar min coefficient according to other bars - between 0 and 1
    val minInList: BigDecimal,

    // bar max coefficient according to other bars - between 0 and 1
    val maxInList: BigDecimal,

    // color - RED or GREEN
    val color: BarColor,
)
