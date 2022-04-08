package com.alexgaas.priceaction.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
@ConditionalOnClass(WebClient::class)
@EnableConfigurationProperties(WebClientConfigurationProperties::class)
class WebClientConfig(
    val properties: WebClientConfigurationProperties
) {
    fun makeHttpClient(): HttpClient {
        @Suppress("DEPRECATION")
        return HttpClient
            .create()
            .tcpConfiguration { tcpClient ->
                tcpClient.option(
                    ChannelOption.CONNECT_TIMEOUT_MILLIS,
                    properties.connectionTimeoutInMillis.toMillis().toInt()
                )
                tcpClient.option(ChannelOption.SO_KEEPALIVE, properties.keepAlive)
                tcpClient.doOnConnected { conn ->
                    conn.apply {
                        addHandlerLast(ReadTimeoutHandler(properties.readTimeoutInMillis.seconds.toInt()))
                        addHandlerLast(WriteTimeoutHandler(properties.writeTimeoutInMillis.seconds.toInt()))
                    }
                }
            }
    }

    @Bean
    fun webClientCustomizer(): WebClientCustomizer {
        return WebClientCustomizer { builder ->
            builder.clientConnector(ReactorClientHttpConnector(makeHttpClient()))
        }
    }
}
