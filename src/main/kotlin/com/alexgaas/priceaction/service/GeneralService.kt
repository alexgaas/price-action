package com.alexgaas.priceaction.service

import com.alexgaas.priceaction.domain.models.general.CheckServerTimeResponse

interface GeneralService {
    suspend fun makePing(): Boolean
    suspend fun getServerTime(): CheckServerTimeResponse
}
