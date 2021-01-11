package com.fireflies.auth_microservice.security

import com.fireflies.auth_microservice.model.User
import com.fireflies.auth_microservice.repository_service.UserRepository
import kotlinx.coroutines.runBlocking
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserPrincipalDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): UserDetails {
        return runBlocking {
            val user: User = userRepository.findByUsername(s) ?: throw UsernameNotFoundException("Username not found: $s")
            UserPrincipal(user)
        }
    }
}