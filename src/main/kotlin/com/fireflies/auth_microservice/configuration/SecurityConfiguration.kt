package com.fireflies.auth_microservice.configuration

import com.fireflies.auth_microservice.repository_service.UserCredentialRepository
import com.fireflies.auth_microservice.security.LoginFilter
import com.fireflies.auth_microservice.security.AuthorizationFilter
import com.fireflies.auth_microservice.security.JwtProperties
import com.fireflies.auth_microservice.security.UserPrincipalDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val userPrincipalDetailsService: UserPrincipalDetailsService, private val userRepository: UserCredentialRepository): WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http // remove csrf and state in session because in jwt we do not need them
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and() // add jwt filters (1. authentication, 2. authorization)
            .addFilter(getJWTAuthenticationFilter())
            .addFilter(AuthorizationFilter(authenticationManager(), userRepository))
            .authorizeRequests() // configure access rules
            .antMatchers(*JwtProperties.AUTHORIZATION_IGNORED_ENDPOINTS).permitAll()
            .antMatchers(JwtProperties.ADMIN_ENDPOINTS).hasRole("ADMIN")
            .anyRequest().authenticated()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder())
        daoAuthenticationProvider.setUserDetailsService(userPrincipalDetailsService)
        return daoAuthenticationProvider
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun getJWTAuthenticationFilter(): LoginFilter {
        val filter = LoginFilter(authenticationManager())
        filter.setFilterProcessesUrl(JwtProperties.LOGIN_ENDPOINT)
        return filter
    }
}
