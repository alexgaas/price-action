package com.alexgaas.priceaction.repo

import com.alexgaas.priceaction.domain.dto.ExchangeInfoDTO
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

interface ExchangeInfoRepository {
    suspend fun findById(id: String): ExchangeInfoDTO?
    suspend fun save(exchangeInfoDTO: ExchangeInfoDTO)
    suspend fun findBySymbols(symbols: List<String>): List<ExchangeInfoDTO>
}

@Repository
interface ReactiveExchangeInfoRepository : ReactiveMongoRepository<ExchangeInfoDTO, ObjectId>
