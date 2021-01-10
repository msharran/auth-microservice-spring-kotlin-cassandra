package com.fireflies.auth_microservice.util

import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.awaitBody

suspend inline fun <reified T> ClientResponse.awaitModel(): T {
    val responseJson = this.awaitBody<String>()
    return objectMapper.readValue(responseJson)
}