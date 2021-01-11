package com.fireflies.auth_microservice.repository_service

import com.fireflies.auth_microservice.model.UserCredential
import com.fireflies.auth_microservice.response.ResourceNotFoundException
import com.fireflies.auth_microservice.util.context
import org.springframework.beans.factory.getBean
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Repository
interface UserCredentialRepository  : CoroutineCrudRepository<UserCredential, String> {
    /* Only Partition Key and Clustering key field queries allowed */
    suspend fun findByUsername(username: String): UserCredential?
}

@Service
class UserCredentialService {
    val repository get() = context.getBean<UserCredentialRepository>()

    suspend fun findActiveUser(username: String): UserCredential? {
        val user = repository.findByUsername(username) ?: throw ResourceNotFoundException("No user found with username: $username")
        return if (user.isActive) user else null
    }

    suspend fun logout(user: UserCredential) : UserCredential {
        user.isLoggedIn = false
        return repository.save(user)
    }

    suspend fun saveToken(user: UserCredential, token: String): UserCredential {
        user.token = token
        return repository.save(user)
    }
}