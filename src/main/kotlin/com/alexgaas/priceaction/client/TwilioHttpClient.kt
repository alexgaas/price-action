package com.alexgaas.priceaction.client

import com.alexgaas.priceaction.domain.models.notification.NotificationContent
import com.alexgaas.priceaction.domain.models.notification.twilio.TwilioWhatsupResponse

interface TwilioHttpClient {
    suspend fun sendWhatsupMessage(
        notificationContent: NotificationContent
    ): List<TwilioWhatsupResponse>
}
