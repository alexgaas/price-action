package com.alexgaas.priceaction.domain.strategy.enums

enum class SignalType {
    ENTER_BY_PATTERN,
    ENTER_BY_STOP_LIMIT_ORDER,
    OBSERVE_ASSET,
    MAKE_STOP_LOSS,
    EXIT_BY_STOP_LOSS,
    EXIT_BY_PATTERN
}
