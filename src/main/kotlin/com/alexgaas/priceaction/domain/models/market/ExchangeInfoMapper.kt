package com.alexgaas.priceaction.domain.models.market

import com.alexgaas.priceaction.domain.dto.ExchangeInfoDTO
import org.bson.types.ObjectId
import java.time.ZoneId

object ExchangeInfoMapper {
    fun toDomain(dto: ExchangeInfoDTO): ExchangeInfo {
        return ExchangeInfo(
            timezone = ZoneId.of(dto.timezone),
            serverTime = dto.serverTime,
            symbols = dto.symbols
        )
    }

    fun toDTO(model: ExchangeInfo): ExchangeInfoDTO {
        return ExchangeInfoDTO(
            id = ObjectId(),
            timezone = model.timezone.id,
            serverTime = model.serverTime,
            symbols = model.symbols
        )
    }
}
