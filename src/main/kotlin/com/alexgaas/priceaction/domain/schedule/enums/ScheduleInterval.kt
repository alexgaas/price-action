package com.alexgaas.priceaction.domain.schedule.enums

enum class ScheduleInterval(val expression: String) {
    EVERY_MIN("0 * * ? * *"),
    EVERY_FIVE_MIN("0 */5 * ? * *"),
    EVERY_FIFTEEN_MIN("0 */15 * ? * *"),
    EVERY_HOUR("0 0 * ? * *"),
    EVERY_DAY("0 0 6 * * ?");
}
