package com.fireflies.auth_microservice.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class APIResponse<T>(val data: T?)

fun <T> responseOf(status: HttpStatus, data: () -> T): ResponseEntity<APIResponse<T>> {
    return ResponseEntity.status(status).body(APIResponse(data()))
}