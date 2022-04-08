package com.alexgaas.priceaction.service.impl

import com.alexgaas.priceaction.client.BinanceHttpClient
import com.alexgaas.priceaction.common.logger
import com.alexgaas.priceaction.config.CandlestickDataConfig
import com.alexgaas.priceaction.domain.models.market.Candlestick
import com.alexgaas.priceaction.domain.models.market.CandlestickMapper
import com.alexgaas.priceaction.domain.models.market.CandlestickSetup
import com.alexgaas.priceaction.domain.models.market.CandlestickSetupMapper
import com.alexgaas.priceaction.domain.models.market.ExchangeInfo
import com.alexgaas.priceaction.domain.models.market.ExchangeInfoMapper
import com.alexgaas.priceaction.domain.models.market.ShortCandlestick
import com.alexgaas.priceaction.domain.models.market.enums.Interval
import com.alexgaas.priceaction.repo.ExchangeInfoRepository
import com.alexgaas.priceaction.repo.MarketDataRepository
import com.alexgaas.priceaction.service.MarketDataService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(CandlestickDataConfig::class)
class MarketDataServiceImpl(
    val binanceClient: BinanceHttpClient,
    val candlestickDataConfig: CandlestickDataConfig,
    val exchangeInfoRepository: ExchangeInfoRepository,
    val marketDataRepository: MarketDataRepository
) : MarketDataService {
    private val log by logger(this::class)

    private suspend fun saveAndReturnCandlesticks(
        candlesticks: List<Candlestick>,
        symbol: String,
        interval: Interval
    ): List<Candlestick> {
        candlesticks.map {
            marketDataRepository.save(CandlestickMapper.toDTO(it, symbol, interval))
        }
        return candlesticks
    }

    override suspend fun setupConfiguredCandlesticks(): List<Candlestick> {
        log.debug("Setup list of candlesticks based on pre-configured setup")
        val settings = CandlestickSetupMapper.toDomain(candlestickDataConfig)
        val array = binanceClient.getCandlestickData(settings)
        return saveAndReturnCandlesticks(array, settings.symbol, settings.interval)
    }

    override suspend fun setupCandlesticks(candlestickSetup: CandlestickSetup): List<Candlestick> {
        log.debug("Setup list of candlesticks based on API body")
        val array = binanceClient.getCandlestickData(candlestickSetup)
        return saveAndReturnCandlesticks(array, candlestickSetup.symbol, candlestickSetup.interval)
    }

    override suspend fun getCandlesticks(candlestickSetup: CandlestickSetup): List<Candlestick> {
        log.debug("Get list of candlesticks based on API body")
        return marketDataRepository.findBySetupAttributes(candlestickSetup).map {
            CandlestickMapper.toDomain(it)
        }
    }

    override suspend fun setupExchangeInfo(symbols: List<String>): ExchangeInfo {
        log.debug("Setup exchange information for symbols [$symbols]")
        val exchangeInfo = binanceClient.getExchangeInfo(symbols)
        log.debug("Save exchange information for symbols [$symbols]")
        exchangeInfoRepository.save(ExchangeInfoMapper.toDTO(exchangeInfo))
        return exchangeInfo
    }

    override suspend fun getExchangeInfo(symbols: List<String>): List<ExchangeInfo> {
        log.debug("Get exchange information for symbols [$symbols]")
        return exchangeInfoRepository.findBySymbols(symbols).map {
            ExchangeInfoMapper.toDomain(it)
        }
    }

    override suspend fun getShortCandlesticks(candlestickSetup: CandlestickSetup): List<ShortCandlestick> {
        log.debug("Get list of candlesticks based on API body for plotting")
        return marketDataRepository.findBySetupAttributes(candlestickSetup).map {
            CandlestickMapper.toShortDomain(it)
        }
    }

    override suspend fun removeCandlesticks(candlestickSetup: CandlestickSetup?) {
        candlestickSetup?.let {
            log.debug("Remove list of candlesticks by setup")
            marketDataRepository.removeBySetupAttributes(it)
        } ?: run {
            log.debug("Remove all candlesticks since setup is null")
            marketDataRepository.removeAll()
        }
    }
}
