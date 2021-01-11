package com.fireflies.auth_microservice

import com.fireflies.auth_microservice.util.context
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
class AuthMicroServiceApplication

fun main(args: Array<String>) {
	context = runApplication<AuthMicroServiceApplication>(*args)
}
