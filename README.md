# Price-Action

kotlin spring boot based application to 
apply price action patterns strategy to fiat assets 
(e.g. bitcoin, ETH) on US binance platform.

Price-Action platform provides signal (using Twillo API by example) to 
identify bearish and bullish pinbar candlesticks.

## Deployment 
Hashicorp Nomad, Vault (for storing sensitive info)

## Tags 
* Kotlin
* Spring Boot
* Quartz 
* Nomad 
* Vault
* MongoDB
* Binance API
* Trading Strategy

##Testing
You may test pattern recognition API using this Jupiter Notebook here - https://github.com/alexgaas/price-action-analytics/blob/main/identify_candlestick_patterns.ipynb

Stock candlestick chart will show you identified pinbar pattern
