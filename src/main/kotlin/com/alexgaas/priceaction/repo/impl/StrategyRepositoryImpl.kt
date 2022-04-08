package com.alexgaas.priceaction.repo.impl

import com.alexgaas.priceaction.domain.dto.StrategyDTO
import com.alexgaas.priceaction.domain.models.market.enums.Interval
import com.alexgaas.priceaction.repo.ReactiveStrategyRepository
import com.alexgaas.priceaction.repo.StrategyRepository
import com.mongodb.assertions.Assertions
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingle
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

@Component
class StrategyRepositoryImpl(
    val reactiveStrategyRepository: ReactiveStrategyRepository
) : StrategyRepository {
    override suspend fun findById(id: String): StrategyDTO? {
        return reactiveStrategyRepository
            .findById(ObjectId(id)).awaitSingle()
    }

    override suspend fun findBySequenceId(id: String): List<StrategyDTO> {
        return reactiveStrategyRepository.findAll().filter {
            it.sequenceId == ObjectId(id)
        }.asFlow().toList()
    }

    override suspend fun findLatestBySymbolAndInterval(symbol: String, interval: Interval): StrategyDTO? {
        return reactiveStrategyRepository.findAll().filter {
            it.symbol == symbol && it.interval == interval && it.latest
        }.awaitFirst()
    }

    override suspend fun findBySymbolAndInterval(symbol: String, interval: Interval): List<StrategyDTO> {
        return reactiveStrategyRepository.findAll().filter {
            it.symbol == symbol && it.interval == interval
        }.asFlow().toList()
    }

    override suspend fun save(strategyDTO: StrategyDTO) {
        val result = reactiveStrategyRepository.save(strategyDTO).awaitSingle()
        Assertions.assertNotNull(result)
    }

    override suspend fun removeAll() {
        reactiveStrategyRepository.deleteAll().awaitSingle()
    }
}
