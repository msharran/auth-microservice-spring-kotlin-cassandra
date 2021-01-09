package com.fireflies.auth_microservice.security

object JwtProperties {
    const val SECRET = "TheFirefliesJwtSecret"
    const val BEARER_ = "Bearer "
    const val AUTHORIZATION = "Authorization"

    const val LOGIN_ENDPOINT = "/api/auth/login"
    const val ADMIN_ENDPOINTS = "/api/account/v1/admin/*"
    val AUTHORIZATION_IGNORED_ENDPOINTS = arrayOf<String>()
}