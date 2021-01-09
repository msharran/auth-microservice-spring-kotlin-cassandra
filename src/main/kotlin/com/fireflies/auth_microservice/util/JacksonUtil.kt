package com.fireflies.auth_microservice.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

val objectMapper: ObjectMapper by lazy {
    val objectMapper = ObjectMapper().registerKotlinModule()
    objectMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
    objectMapper
}