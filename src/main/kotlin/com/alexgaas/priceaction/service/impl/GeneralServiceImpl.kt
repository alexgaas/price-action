package com.alexgaas.priceaction.service.impl

import com.alexgaas.priceaction.client.BinanceHttpClient
import com.alexgaas.priceaction.common.logger
import com.alexgaas.priceaction.domain.models.general.CheckServerTimeResponse
import com.alexgaas.priceaction.service.GeneralService
import org.springframework.stereotype.Service

@Service
class GeneralServiceImpl(
    val binanceClient: BinanceHttpClient,
) : GeneralService {
    private val log by logger(this::class)

    override suspend fun makePing(): Boolean {
        log.debug("Test connectivity")
        return binanceClient.testConnectivity()
    }

    override suspend fun getServerTime(): CheckServerTimeResponse {
        log.debug("Get server time in UTC format")
        return binanceClient.checkServerTime()
    }
}
