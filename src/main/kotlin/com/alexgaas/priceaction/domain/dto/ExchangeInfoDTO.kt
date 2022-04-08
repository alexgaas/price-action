package com.alexgaas.priceaction.domain.dto

import com.alexgaas.priceaction.domain.models.market.ExchangeInfoSymbol
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("exchangeInfo")
data class ExchangeInfoDTO(
    @Id
    @JsonSerialize(using = ToStringSerializer::class)
    val id: ObjectId,

    val timezone: String,
    val serverTime: Long,
    val symbols: List<ExchangeInfoSymbol>,
)
