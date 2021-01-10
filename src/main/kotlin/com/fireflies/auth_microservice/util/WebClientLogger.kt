package com.fireflies.auth_microservice.util

import org.springframework.http.codec.LoggingCodecSupport
import org.springframework.web.reactive.function.client.ExchangeStrategies

object WebClientLogger {
    fun exchangeStrategies(): ExchangeStrategies {
        val exchangeStrategies = ExchangeStrategies.withDefaults()
        exchangeStrategies
            .messageWriters().stream()
            .filter(LoggingCodecSupport::class::isInstance)
            .forEach { writer ->
                (writer as LoggingCodecSupport).isEnableLoggingRequestDetails = true
            }
        return exchangeStrategies
    }
}