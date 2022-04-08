package com.alexgaas.priceaction.service

import com.alexgaas.priceaction.domain.models.notification.NotificationTarget
import com.alexgaas.priceaction.domain.models.notification.enums.NotificationType

interface NotificationService {
    suspend fun sendNotification(
        notificationTargets: List<NotificationTarget>,
        notificationType: NotificationType
    )
}
