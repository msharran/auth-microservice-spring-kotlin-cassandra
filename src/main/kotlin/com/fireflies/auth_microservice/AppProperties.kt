package com.fireflies.auth_microservice

object AppProperties {
    const val MICROSERVICE_URL_NAME = "auth"
    const val MICROSERVICE_NAME = "auth_microservice"
    const val BASE_URL = "/api/$MICROSERVICE_URL_NAME/v1"

    object Cassandra {
        const val KEYSPACE_NAME = MICROSERVICE_NAME
        const val REPLICATION_FACTOR = 1L
        const val NODES = "127.0.0.1:9042" // comma separated nodes
        val MODELS = arrayOf("com.fireflies.auth_microservice.model")
    }

    object Security {
        const val SECRET = "TheFirefliesJwtSecret"
        const val BEARER_ = "Bearer "
        const val AUTHORIZATION = "Authorization"
        const val LOGIN_ENDPOINT = "/api/auth/login"
        const val ADMIN_ENDPOINTS = "/api/$MICROSERVICE_URL_NAME/v1/admin/*"
        val AUTHORIZATION_IGNORED_ENDPOINTS = arrayOf<String>()
    }
}