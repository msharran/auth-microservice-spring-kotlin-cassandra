package com.fireflies.auth_microservice.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fireflies.auth_microservice.AppProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.web.reactive.function.client.WebClient


/**
 * Global objects
 */

lateinit var context: ConfigurableApplicationContext

val log: Logger by lazy {
    LoggerFactory.getLogger(AppProperties.MICROSERVICE_NAME)
}

val objectMapper: ObjectMapper by lazy {
    val objectMapper = ObjectMapper().registerKotlinModule()
    objectMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
    objectMapper
}

fun webClientOf(baseUrl: String) = WebClient
    .builder()
    .baseUrl(baseUrl)
    .exchangeStrategies(WebClientLogger.exchangeStrategies())
    .build()