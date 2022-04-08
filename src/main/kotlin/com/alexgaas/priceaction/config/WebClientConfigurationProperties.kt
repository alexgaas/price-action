package com.alexgaas.priceaction.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "web.client")
class WebClientConfigurationProperties {
    lateinit var connectionTimeoutInMillis: Duration
    lateinit var readTimeoutInMillis: Duration
    lateinit var writeTimeoutInMillis: Duration
    var keepAlive = false
}
