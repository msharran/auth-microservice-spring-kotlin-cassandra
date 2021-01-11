package com.fireflies.auth_microservice.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class APIResponse<T>(
    var hasNext: Boolean = false,
    var data: T
)

fun <T> responseOf(status: HttpStatus, hasNext: Boolean = false, data: (() -> T)? = null): ResponseEntity<APIResponse<T>> {
    data?.let {
        return ResponseEntity.status(status).body(APIResponse(hasNext, it()))
    } ?: return ResponseEntity.status(status).build()
}