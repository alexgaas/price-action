package com.alexgaas.priceaction.domain.models.market

import com.alexgaas.priceaction.domain.dto.CandlestickDTO
import com.alexgaas.priceaction.domain.models.market.enums.Interval
import org.bson.types.ObjectId
import java.time.Instant

object CandlestickMapper {
    fun toDomain(candlestickDTO: CandlestickDTO): Candlestick {
        return Candlestick(
            openTime = candlestickDTO.openTime,
            openPrice = candlestickDTO.openPrice,
            highPrice = candlestickDTO.highPrice,
            lowPrice = candlestickDTO.lowPrice,
            closePrice = candlestickDTO.closePrice,
            volume = candlestickDTO.volume,
            closeTime = candlestickDTO.closeTime,
            quoteAssetVolume = candlestickDTO.quoteAssetVolume,
            numberOfTrades = candlestickDTO.numberOfTrades,
            takerBuyBaseAssetVolume = candlestickDTO.takerBuyBaseAssetVolume,
            takerBuyQuoteAssetVolume = candlestickDTO.takerBuyQuoteAssetVolume
        )
    }

    fun toShortDomain(candlestickDTO: CandlestickDTO): ShortCandlestick {
        return ShortCandlestick(
            datetime = Instant.ofEpochMilli(candlestickDTO.openTime),
            openPrice = candlestickDTO.openPrice,
            highPrice = candlestickDTO.highPrice,
            lowPrice = candlestickDTO.lowPrice,
            closePrice = candlestickDTO.closePrice,
            adjClosePrice = candlestickDTO.closePrice,
            volume = candlestickDTO.volume
        )
    }

    fun toDTO(candlestick: Candlestick, symbol: String, interval: Interval): CandlestickDTO {
        return CandlestickDTO(
            id = ObjectId(),
            symbol = symbol,
            interval = interval,
            openTime = candlestick.openTime,
            openPrice = candlestick.openPrice,
            highPrice = candlestick.highPrice,
            lowPrice = candlestick.lowPrice,
            closePrice = candlestick.closePrice,
            volume = candlestick.volume,
            closeTime = candlestick.closeTime,
            quoteAssetVolume = candlestick.quoteAssetVolume,
            numberOfTrades = candlestick.numberOfTrades,
            takerBuyBaseAssetVolume = candlestick.takerBuyBaseAssetVolume,
            takerBuyQuoteAssetVolume = candlestick.takerBuyQuoteAssetVolume
        )
    }
}
