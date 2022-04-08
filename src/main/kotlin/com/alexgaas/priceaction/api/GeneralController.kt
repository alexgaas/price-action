package com.alexgaas.priceaction.api

import com.alexgaas.priceaction.domain.models.general.CheckServerTimeResponse
import com.alexgaas.priceaction.service.GeneralService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GeneralController(
    val generalService: GeneralService
) {
    @GetMapping("/ping")
    suspend fun testConnectivity(): Boolean {
        return generalService.makePing()
    }

    @GetMapping("/server-time")
    suspend fun getServerTime(): CheckServerTimeResponse {
        return generalService.getServerTime()
    }
}
