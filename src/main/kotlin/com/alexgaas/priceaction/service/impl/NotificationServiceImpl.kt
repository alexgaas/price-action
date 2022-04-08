package com.alexgaas.priceaction.service.impl

import com.alexgaas.priceaction.client.TwilioHttpClient
import com.alexgaas.priceaction.common.logger
import com.alexgaas.priceaction.config.NotificationConfigurationProperties
import com.alexgaas.priceaction.domain.models.notification.NotificationMapper
import com.alexgaas.priceaction.domain.models.notification.NotificationTarget
import com.alexgaas.priceaction.domain.models.notification.enums.NotificationType
import com.alexgaas.priceaction.service.NotificationService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(NotificationConfigurationProperties::class)
class NotificationServiceImpl(
    val twilioHttpClient: TwilioHttpClient,
    val notificationConfigurationProperties: NotificationConfigurationProperties
) : NotificationService {
    private val log by logger(this::class)

    override suspend fun sendNotification(
        notificationTargets: List<NotificationTarget>,
        notificationType: NotificationType
    ) {
        log.info("Send message [$notificationTargets] with type [$notificationType]")
        val notificationContent = NotificationMapper.toContent(
            notificationTargets,
            notificationConfigurationProperties,
            notificationType
        )
        twilioHttpClient.sendWhatsupMessage(notificationContent)
    }
}
