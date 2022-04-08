package com.alexgaas.priceaction.repo.impl
import com.alexgaas.priceaction.domain.dto.ExchangeInfoDTO
import com.alexgaas.priceaction.repo.ExchangeInfoRepository
import com.alexgaas.priceaction.repo.ReactiveExchangeInfoRepository
import com.mongodb.assertions.Assertions.assertNotNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

@Component
class ExchangeInfoRepositoryImpl(
    val reactiveExchangeInfoRepository: ReactiveExchangeInfoRepository
) : ExchangeInfoRepository {
    override suspend fun findById(id: String): ExchangeInfoDTO? {
        return reactiveExchangeInfoRepository
            .findById(ObjectId(id)).awaitSingle()
    }

    override suspend fun save(exchangeInfoDTO: ExchangeInfoDTO) {
        val result = reactiveExchangeInfoRepository.save(exchangeInfoDTO).awaitSingle()
        assertNotNull(result)
    }

    override suspend fun findBySymbols(symbols: List<String>): List<ExchangeInfoDTO> {
        return if (symbols.count() == 0) {
            reactiveExchangeInfoRepository.findAll().asFlow().toList()
        } else {
            reactiveExchangeInfoRepository.findAll().filter {
                it.symbols.map {
                    symbols.contains(it.symbol)
                }.count() > 0
            }
                .asFlow().toList().sortedByDescending {
                    it.serverTime
                }
        }
    }
}
