package com.alexgaas.priceaction.repo

import com.alexgaas.priceaction.domain.dto.StrategyDTO
import com.alexgaas.priceaction.domain.models.market.enums.Interval
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

interface StrategyRepository {
    suspend fun findById(id: String): StrategyDTO?
    suspend fun findBySequenceId(id: String): List<StrategyDTO>
    suspend fun findLatestBySymbolAndInterval(symbol: String, interval: Interval): StrategyDTO?
    suspend fun findBySymbolAndInterval(symbol: String, interval: Interval): List<StrategyDTO>
    suspend fun save(strategyDTO: StrategyDTO)
    suspend fun removeAll()
}

@Repository
interface ReactiveStrategyRepository : ReactiveMongoRepository<StrategyDTO, ObjectId>
