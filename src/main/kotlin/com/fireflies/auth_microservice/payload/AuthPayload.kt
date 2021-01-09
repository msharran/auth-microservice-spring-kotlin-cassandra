package com.fireflies.auth_microservice.payload

data class LoginPayload(val username: String, val password: String)

data class LogoutPayload(val username: String)
