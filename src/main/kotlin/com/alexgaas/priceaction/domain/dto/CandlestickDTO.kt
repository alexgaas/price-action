package com.alexgaas.priceaction.domain.dto

import com.alexgaas.priceaction.domain.models.market.enums.Interval
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat
import java.math.BigDecimal
import java.math.BigInteger
import java.time.Instant

@Document("marketData")
data class CandlestickDTO(
    @Id
    @JsonSerialize(using = ToStringSerializer::class)
    val id: ObjectId,

    // common candlestick information
    val symbol: String,
    val interval: Interval,

    @Indexed
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val timestamp: Instant = Instant.now(),

    val openTime: Long,
    val openPrice: BigDecimal,
    val highPrice: BigDecimal,
    val lowPrice: BigDecimal,
    val closePrice: BigDecimal,
    val volume: BigDecimal,
    val closeTime: Long,
    val quoteAssetVolume: BigDecimal,
    val numberOfTrades: BigInteger,
    val takerBuyBaseAssetVolume: BigDecimal,
    val takerBuyQuoteAssetVolume: BigDecimal
)
