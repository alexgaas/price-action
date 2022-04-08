package com.alexgaas.priceaction.domain.models.notification

import com.alexgaas.priceaction.domain.models.notification.enums.NotificationType

data class NotificationContent(
    val body: String,
    val type: NotificationType
)
