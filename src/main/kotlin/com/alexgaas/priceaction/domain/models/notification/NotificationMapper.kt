package com.alexgaas.priceaction.domain.models.notification

import com.alexgaas.priceaction.config.NotificationConfigurationProperties
import com.alexgaas.priceaction.domain.models.notification.enums.NotificationType
import java.text.MessageFormat

object NotificationMapper {
    fun toContent(
        notificationTargets: List<NotificationTarget>,
        notificationConfigurationProperties: NotificationConfigurationProperties,
        notificationType: NotificationType
    ): NotificationContent {
        val body = notificationTargets.map { target ->
            val message = notificationConfigurationProperties.patterns.first { item ->
                item.id == target.id
            }.body
            MessageFormat.format(
                message,
                target.symbol,
                target.color,
                target.interval
            )
        }.toList().joinToString(", ")

        return NotificationContent(
            body = body,
            type = notificationType
        )
    }
}
