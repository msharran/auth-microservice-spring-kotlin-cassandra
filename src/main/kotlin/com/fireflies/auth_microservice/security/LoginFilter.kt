package com.fireflies.auth_microservice.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC512
import com.fireflies.auth_microservice.response.UnAuthorizedException
import com.fireflies.auth_microservice.payload.LoginPayload
import com.fireflies.auth_microservice.repository_service.UserCredentialService
import com.fireflies.auth_microservice.response.APIResponse
import com.fireflies.auth_microservice.util.objectMapper
import org.json.JSONObject
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class LoginFilter(authenticationManager: AuthenticationManager) :
    UsernamePasswordAuthenticationFilter(authenticationManager) {

    init {
        super.setAuthenticationFailureHandler(JwtAuthenticationFailureHandler())
    }

    /** Trigger when we issue POST request to [JwtProperties.LOGIN_ENDPOINT]
    We also need to pass in {"username":"janedoe@fireflies.com", "password":"doe123"} in the request body
     */
    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {

        val credentials: LoginPayload
        try {
            credentials = objectMapper.readValue(request.inputStream, LoginPayload::class.java)
        } catch (e: IOException) {
            e.printStackTrace()
            throw UnAuthorizedException(e.localizedMessage)
        }
        val usernamePasswordAsToken = UsernamePasswordAuthenticationToken(
            credentials.username,
            credentials.password,
            ArrayList())
        return authenticationManager.authenticate(usernamePasswordAsToken)
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        chain: FilterChain?,
        authResult: Authentication,
    ) {
        val principal: UserPrincipal = authResult.principal as UserPrincipal
        val token = JWT.create()
            .withSubject(principal.username)
            .sign(HMAC512(JwtProperties.SECRET.toByteArray()))
        val json = objectMapper.writeValueAsString(
            APIResponse(
            UserCredentialService().saveToken(principal.user, token)
        )
        )
        response.writer.append(json)
    }

    inner class JwtAuthenticationFailureHandler : AuthenticationFailureHandler {
        @Throws(IOException::class, ServletException::class)
        override fun onAuthenticationFailure(
            request: HttpServletRequest,
            response: HttpServletResponse,
            exception: AuthenticationException,
        ) {
            response.status = 403
            response.contentType = "application/json"
            response.writer.append(unAuthorizedResponse(exception))
        }

        private fun unAuthorizedResponse(exception: AuthenticationException): String {
            return JSONObject(APIResponse(exception.localizedMessage)).toString()
        }
    }
}