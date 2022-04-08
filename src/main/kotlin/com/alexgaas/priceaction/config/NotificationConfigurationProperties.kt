package com.alexgaas.priceaction.config

import com.alexgaas.priceaction.domain.pattern.PatternType
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("notifications")
data class NotificationConfigurationProperties(
    val messages: MutableList<MessageConfigurationItem>
) {
    data class MessageConfigurationItem(
        val id: PatternType,
        val body: String
    )
}
