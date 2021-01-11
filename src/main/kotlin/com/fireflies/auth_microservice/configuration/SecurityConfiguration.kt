package com.fireflies.auth_microservice.configuration

import com.fireflies.auth_microservice.AppProperties
import com.fireflies.auth_microservice.repository_service.UserService
import com.fireflies.auth_microservice.security.LoginFilter
import com.fireflies.auth_microservice.security.AuthorizationFilter
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
class SecurityConfiguration(private val userPrincipalDetailsService: UserPrincipalDetailsService, private val userService: UserService): WebSecurityConfigurerAdapter() {
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
            .addFilter(AuthorizationFilter(authenticationManager(), userService))
            .authorizeRequests() // configure access rules
            .antMatchers(*AppProperties.Security.AUTHORIZATION_IGNORED_ENDPOINTS).permitAll()
            .antMatchers(AppProperties.Security.ADMIN_ENDPOINTS).hasRole("ADMIN")
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
        filter.setFilterProcessesUrl(AppProperties.Security.LOGIN_ENDPOINT)
        return filter
    }
}
