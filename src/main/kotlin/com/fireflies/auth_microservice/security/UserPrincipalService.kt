package com.fireflies.auth_microservice.security

import com.fireflies.auth_microservice.model.UserCredential
import com.fireflies.auth_microservice.repository_service.UserCredentialRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserPrincipalDetailsService(private val userRepository: UserCredentialRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): UserDetails {
        val user: UserCredential = userRepository.findByUsername(s).orElseThrow { UsernameNotFoundException("Username not found: $s") }
        return UserPrincipal(user)
    }
}