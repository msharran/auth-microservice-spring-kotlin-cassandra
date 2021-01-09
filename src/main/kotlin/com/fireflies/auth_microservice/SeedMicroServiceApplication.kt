package com.fireflies.auth_microservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext

@SpringBootApplication
class SeedMicroServiceApplication

fun main(args: Array<String>) {
	context = runApplication<SeedMicroServiceApplication>(*args)
}

lateinit var context: ConfigurableApplicationContext
