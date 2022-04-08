package com.alexgaas.priceaction.domain.models.market

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.math.BigInteger
import java.time.Instant

/*
** json example:
*
    1499040000000,      // Open time
    "0.01634790",       // Open
    "0.80000000",       // High
    "0.01575800",       // Low
    "0.01577100",       // Close
    "148976.11427815",  // Volume
    1499644799999,      // Close time
    "2434.19055334",    // Quote asset volume
    308,                // Number of trades
    "1756.87402397",    // Taker buy base asset volume
    "28.46694368",      // Taker buy quote asset volume
    "17928899.62484339" // Ignore.
 */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
data class Candlestick(
    @JsonProperty(index = 0)
    val openTime: Long,

    @JsonProperty(index = 1)
    val openPrice: BigDecimal,

    @JsonProperty(index = 2)
    val highPrice: BigDecimal,

    @JsonProperty(index = 3)
    val lowPrice: BigDecimal,

    @JsonProperty(index = 4)
    val closePrice: BigDecimal,

    @JsonProperty(index = 5)
    val volume: BigDecimal,

    @JsonProperty(index = 6)
    val closeTime: Long,

    @JsonProperty(index = 7)
    val quoteAssetVolume: BigDecimal,

    @JsonProperty(index = 8)
    val numberOfTrades: BigInteger,

    @JsonProperty(index = 9)
    val takerBuyBaseAssetVolume: BigDecimal,

    @JsonProperty(index = 10)
    val takerBuyQuoteAssetVolume: BigDecimal
)

/*
** json example:
*
Date,Open,High,Low,Close,Adj Close,Volume
2018-01-02,1048.339966,1066.939941,1045.229980,1065.000000,1065.000000,1237600
 */
data class ShortCandlestick(
    @JsonProperty("Date")
    val datetime: Instant,

    @JsonProperty("Open")
    val openPrice: BigDecimal,

    @JsonProperty("High")
    val highPrice: BigDecimal,

    @JsonProperty("Low")
    val lowPrice: BigDecimal,

    @JsonProperty("Close")
    val closePrice: BigDecimal,

    @JsonProperty("Adj Close")
    val adjClosePrice: BigDecimal,

    @JsonProperty("Volume")
    val volume: BigDecimal
)
