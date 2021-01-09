package com.fireflies.auth_microservice.controller.v1

import com.fireflies.auth_microservice.AppProperties
import com.fireflies.auth_microservice.model.UserCredential
import com.fireflies.auth_microservice.payload.LogoutPayload
import com.fireflies.auth_microservice.repository_service.UserCredentialService
import com.fireflies.auth_microservice.response.APIResponse
import com.fireflies.auth_microservice.response.BadRequestException
import com.fireflies.auth_microservice.response.responseOf
import kotlinx.coroutines.coroutineScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient


@RestController
@RequestMapping(AppProperties.BASE_URL)
class AuthController {
    @Autowired
    lateinit var service: UserCredentialService

    private val client = WebClient.create()

    @PostMapping("/logout")
    suspend fun logout(@RequestBody logoutPayload: LogoutPayload): ResponseEntity<APIResponse<String>> = coroutineScope {
        val user = service.findActiveUser(username = logoutPayload.username) ?: throw BadRequestException("User already logged out!")
        service.logout(user)
        responseOf(HttpStatus.CREATED) {
            "Successfully logged out"
        }
    }

    @GetMapping("/user")
    suspend fun getUser(@RequestParam(required = true) username: String): ResponseEntity<APIResponse<UserCredential>> = coroutineScope {
        val user = service.findActiveUser(username = username) ?: throw BadRequestException("User logged out!")
        responseOf(HttpStatus.OK) {
            user
        }
    }
}