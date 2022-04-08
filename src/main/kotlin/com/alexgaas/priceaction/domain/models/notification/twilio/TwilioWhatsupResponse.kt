package com.alexgaas.priceaction.domain.models.notification.twilio

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

/*
full response example:

{
  "account_sid": "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
  "api_version": "2010-04-01",
  "body": "Hello there!",
  "date_created": "Thu, 30 Jul 2015 20:12:31 +0000",
  "date_sent": "Thu, 30 Jul 2015 20:12:33 +0000",
  "date_updated": "Thu, 30 Jul 2015 20:12:33 +0000",
  "direction": "outbound-api",
  "error_code": null,
  "error_message": null,
  "from": "whatsapp:+14155238886",
  "messaging_service_sid": "MGXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
  "num_media": "0",
  "num_segments": "1",
  "price": null,
  "price_unit": null,
  "sid": "SMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
  "status": "sent",
  "subresource_uris": {
    "media": "/2010-04-01/Accounts/ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Messages/SMXXXXXXXXXXXXXXXXXXXXXXXXXXX/Media.json"
  },
  "to": "whatsapp:+15005550006",
  "uri": "/2010-04-01/Accounts/ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Messages/SMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.json"
}

 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class TwilioWhatsupResponse(
    val body: String,

    @JsonProperty("date_sent")
    val dateSent: Instant?,

    @JsonProperty("error_code")
    val errorCode: String?,

    @JsonProperty("error_message")
    val errorMessage: String?,

    val from: String,

    val price: String?,

    @JsonProperty("price_unit")
    val priceUnit: String?,

    val status: String,

    val to: String
)
