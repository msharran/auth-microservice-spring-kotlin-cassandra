package com.fireflies.auth_microservice.response

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ResourceNotFoundException(msg: String) : ResponseStatusException(HttpStatus.NOT_FOUND, msg)

class UnAuthorizedException(msg: String) : ResponseStatusException(HttpStatus.UNAUTHORIZED, msg)

class BadRequestException(msg: String) : ResponseStatusException(HttpStatus.BAD_REQUEST, msg)