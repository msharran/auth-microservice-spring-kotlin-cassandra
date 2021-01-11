package com.fireflies.auth_microservice.controller.v1

import com.fireflies.auth_microservice.AppProperties
import com.fireflies.auth_microservice.model.User
import com.fireflies.auth_microservice.repository_service.UserService
import com.fireflies.auth_microservice.response.APIResponse
import com.fireflies.auth_microservice.response.ResourceNotFoundException
import com.fireflies.auth_microservice.response.responseOf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(AppProperties.BASE_URL)
class AuthController {
    @Autowired
    lateinit var service: UserService

    @PutMapping("/logout/{username}")
    suspend fun logout(@PathVariable username: String): ResponseEntity<APIResponse<Unit>> {
        val user = service.findActiveUser(username = username)!!
        service.logout(user)
        return responseOf(HttpStatus.NO_CONTENT)
    }

    @GetMapping("/user/{username}")
    suspend fun getUser(@PathVariable username: String): ResponseEntity<APIResponse<User>> {
        val user = service.findActiveUser(username = username) ?: throw ResourceNotFoundException("User not found")
        return responseOf(HttpStatus.OK) {
            user
        }
    }
}