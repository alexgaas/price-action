package com.alexgaas.priceaction.repo

import com.alexgaas.priceaction.domain.dto.CandlestickDTO
import com.alexgaas.priceaction.domain.models.market.CandlestickSetup
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

interface MarketDataRepository {
    suspend fun findById(id: String): CandlestickDTO?
    suspend fun save(candlestickDTO: CandlestickDTO)
    suspend fun findBySetupAttributes(candlestickSetup: CandlestickSetup): List<CandlestickDTO>
    suspend fun removeBySetupAttributes(candlestickSetup: CandlestickSetup)
    suspend fun removeAll()
}

@Repository
interface ReactiveMarketDataRepository : ReactiveMongoRepository<CandlestickDTO, ObjectId>
