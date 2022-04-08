package com.alexgaas.priceaction

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PriceActionApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<PriceActionApplication>(*args)
}
