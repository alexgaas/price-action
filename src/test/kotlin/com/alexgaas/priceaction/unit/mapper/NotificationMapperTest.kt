package com.alexgaas.priceaction.unit.mapper

import com.alexgaas.priceaction.config.NotificationConfigurationProperties
import com.alexgaas.priceaction.domain.models.market.enums.Interval
import com.alexgaas.priceaction.domain.models.notification.NotificationMapper
import com.alexgaas.priceaction.domain.models.notification.NotificationTarget
import com.alexgaas.priceaction.domain.models.notification.enums.NotificationType
import com.alexgaas.priceaction.domain.pattern.BarColor
import com.alexgaas.priceaction.domain.pattern.PatternType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.text.MessageFormat

class NotificationMapperTest {
    @Test
    fun testToContent() {
        val notificationTargets: List<NotificationTarget> = listOf(
            NotificationTarget(
                id = PatternType.LONG_PINBAR_DOWN,
                symbol = "BTCUSD",
                interval = Interval.ONE_MIN,
                color = BarColor.GREEN
            )
        )
        val notificationConfigurationProperties = NotificationConfigurationProperties(
            messages = mutableListOf(
                NotificationConfigurationProperties.MessageConfigurationItem(
                    id = PatternType.LONG_PINBAR_DOWN,
                    body = "Long pinbar down (BUY) for {0}, color {1}, interval {2}"
                )
            )
        )

        val notificationType: NotificationType = NotificationType.TWILLO_WHATSUP_MESSAGE
        val result = NotificationMapper.toContent(
            notificationTargets, notificationConfigurationProperties, notificationType
        )
        assertEquals(
            MessageFormat.format(
                "Long pinbar down (BUY) for {0}, color {1}, interval {2}",
                "BTCUSD",
                BarColor.GREEN,
                Interval.ONE_MIN,
            ),
            result.body
        )
    }
}
