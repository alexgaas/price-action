package com.alexgaas.priceaction.domain.models.notification

import com.alexgaas.priceaction.domain.models.market.enums.Interval
import com.alexgaas.priceaction.domain.models.notification.enums.MessageType
import com.alexgaas.priceaction.domain.pattern.BarColor
import java.math.BigDecimal

data class NotificationTarget(
    val id: MessageType,
    val symbol: String? = null,
    val color: BarColor? = null,
    val interval: Interval? = null,
    val stopLimit: BigDecimal? = null
)
