package com.alexgaas.priceaction.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("twilio.api")
@ConstructorBinding
data class TwilioConfig(
    val host: String,
    val accountSid: String,
    val authToken: String,
    val account: Account
) {
    data class Account(
        val defaultFrom: String,
        val defaultTo: List<String>
    )
}
