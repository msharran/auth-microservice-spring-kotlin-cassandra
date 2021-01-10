package com.fireflies.auth_microservice

import com.fireflies.auth_microservice.util.context
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthMicroServiceApplication

fun main(args: Array<String>) {
	context = runApplication<AuthMicroServiceApplication>(*args)
}
