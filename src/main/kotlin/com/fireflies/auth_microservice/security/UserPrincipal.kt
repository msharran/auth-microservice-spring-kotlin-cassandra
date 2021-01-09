package com.fireflies.auth_microservice.security

import com.fireflies.auth_microservice.model.UserCredential

import org.springframework.security.core.GrantedAuthority

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*


class UserPrincipal(val user: UserCredential) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        val authority: GrantedAuthority = SimpleGrantedAuthority(user.role.name)
        authorities.add(authority)
        return authorities
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return user.isActive
    }

    val userType: String
        get() = user.userType.name

    val userId: UUID
        get() = user.userId
}