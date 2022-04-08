package com.alexgaas.priceaction.client.impl

import com.alexgaas.priceaction.client.TwilioHttpClient
import com.alexgaas.priceaction.config.TwilioConfig
import com.alexgaas.priceaction.domain.models.notification.NotificationContent
import com.alexgaas.priceaction.domain.models.notification.twilio.TwilioWhatsupResponse
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Service
@Configuration
@EnableConfigurationProperties(TwilioConfig::class)
class TwilioHttpClientImpl(
    val twilioConfig: TwilioConfig,
    webClientBuilder: WebClient.Builder
) : TwilioHttpClient {
    var webClient: WebClient = webClientBuilder
        .baseUrl(twilioConfig.host)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .build()

    override suspend fun sendWhatsupMessage(notificationContent: NotificationContent): List<TwilioWhatsupResponse> {
        return twilioConfig.account.defaultTo.map { sendTo ->
            webClient.post()
                .headers { header ->
                    header.setBasicAuth(twilioConfig.accountSid, twilioConfig.authToken)
                }
                .body(
                    BodyInserters
                        .fromFormData("From", twilioConfig.account.defaultFrom)
                        .with("To", sendTo)
                        .with("Body", notificationContent.body)
                )
                .retrieve()
                .awaitBody()
        }
    }
}
