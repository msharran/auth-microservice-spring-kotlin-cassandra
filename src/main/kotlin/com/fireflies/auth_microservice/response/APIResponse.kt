package com.fireflies.auth_microservice.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class APIResponse<T>(
    var hasNext: Boolean = false,
    var data: T
)

fun <T> responseOf(status: HttpStatus, hasNext: Boolean = false, data: () -> T): ResponseEntity<APIResponse<T>> {
    return ResponseEntity.status(status).body(APIResponse(hasNext, data()))
}