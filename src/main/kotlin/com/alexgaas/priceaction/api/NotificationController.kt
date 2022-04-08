package com.alexgaas.priceaction.api

import com.alexgaas.priceaction.domain.models.notification.NotificationTarget
import com.alexgaas.priceaction.domain.models.notification.enums.NotificationType
import com.alexgaas.priceaction.service.NotificationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class NotificationController(
    val notificationService: NotificationService
) {
    @PostMapping("/send-test-notification")
    suspend fun sendConfiguredTestNotification(
        @RequestBody notificationTarget: NotificationTarget
    ) {
        notificationService.sendNotification(
            listOf(notificationTarget),
            NotificationType.TWILLO_WHATSUP_MESSAGE
        )
    }
}
