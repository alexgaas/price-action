package com.alexgaas.priceaction.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("schedule")
data class ScheduleConfigurationProperties(
    val symbol: String,
    val scheduleToggle: ScheduleToggle
) {
    data class ScheduleToggle(
        val oneMinEnabled: Boolean = false,
        val fiveMinEnabled: Boolean = false,
        val fifteenMinEnabled: Boolean = false,
        val oneHourEnabled: Boolean = false,
        val oneDayEnabled: Boolean = false,
    )
}
