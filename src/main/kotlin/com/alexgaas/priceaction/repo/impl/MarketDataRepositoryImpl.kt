package com.alexgaas.priceaction.repo.impl

import com.alexgaas.priceaction.domain.dto.CandlestickDTO
import com.alexgaas.priceaction.domain.models.market.CandlestickSetup
import com.alexgaas.priceaction.repo.MarketDataRepository
import com.alexgaas.priceaction.repo.ReactiveMarketDataRepository
import com.mongodb.assertions.Assertions
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingle
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

const val DEFAULT_LIMIT = 500

@Component
class MarketDataRepositoryImpl(
    val reactiveMarketDataRepository: ReactiveMarketDataRepository
) : MarketDataRepository {
    override suspend fun findById(id: String): CandlestickDTO? {
        return reactiveMarketDataRepository
            .findById(ObjectId(id)).awaitSingle()
    }

    override suspend fun save(candlestickDTO: CandlestickDTO) {
        val result = reactiveMarketDataRepository.save(candlestickDTO).awaitSingle()
        Assertions.assertNotNull(result)
    }

    override suspend fun findBySetupAttributes(candlestickSetup: CandlestickSetup): List<CandlestickDTO> {
        return reactiveMarketDataRepository.findAll().filter {
            it.symbol == candlestickSetup.symbol &&
                it.interval == candlestickSetup.interval &&
                it.closeTime in (candlestickSetup.startTime ?: 0)..(candlestickSetup.endTime ?: Long.MAX_VALUE)
        }.asFlow().toList().takeLast(candlestickSetup.limit ?: DEFAULT_LIMIT)
    }

    override suspend fun removeAll() {
        reactiveMarketDataRepository.deleteAll().awaitSingle()
    }

    override suspend fun removeBySetupAttributes(candlestickSetup: CandlestickSetup) {
        findBySetupAttributes(candlestickSetup).map {
            reactiveMarketDataRepository.deleteById(it.id).awaitSingle()
        }
    }
}
