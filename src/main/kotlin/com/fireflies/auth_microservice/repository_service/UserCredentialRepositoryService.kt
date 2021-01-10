package com.fireflies.auth_microservice.repository_service

import com.fireflies.auth_microservice.model.UserCredential
import com.fireflies.auth_microservice.response.ResourceNotFoundException
import com.fireflies.auth_microservice.util.context
import org.springframework.beans.factory.getBean
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.*

@Repository
interface UserCredentialRepository  : CassandraRepository<UserCredential, String> {
    //Only Partition Key and Clustering key field queries allowed
    fun findByUsername(username: String): Optional<UserCredential>
}

@Service
class UserCredentialService {
    val repository get() = context.getBean<UserCredentialRepository>()

    fun findActiveUser(username: String): UserCredential? {
        val user = repository.findByUsername(username).orElseThrow { ResourceNotFoundException("No user found with username: $username") }
        return if (user.isActive) {
            user
        } else {
            null
        }
    }

    fun logout(user: UserCredential) : UserCredential {
        user.isLoggedIn = false
        return repository.save(user)
    }

    fun saveToken(user: UserCredential, token: String): UserCredential {
        user.token = token
        return repository.save(user)
    }
}