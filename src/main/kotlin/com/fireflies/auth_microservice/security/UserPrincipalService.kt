package com.fireflies.auth_microservice.security

import com.fireflies.auth_microservice.model.UserCredential
import com.fireflies.auth_microservice.repository_service.UserCredentialRepository
import kotlinx.coroutines.runBlocking
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserPrincipalDetailsService(private val userRepository: UserCredentialRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): UserDetails {
        return runBlocking {
            val user: UserCredential = userRepository.findByUsername(s) ?: throw UsernameNotFoundException("Username not found: $s")
            UserPrincipal(user)
        }
    }
}