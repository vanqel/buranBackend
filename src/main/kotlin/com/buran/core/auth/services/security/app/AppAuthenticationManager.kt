package com.buran.core.auth.services.security.app

import com.buran.core.auth.dto.login.LoginInput
import com.buran.core.auth.errors.AuthError
import com.buran.core.auth.utils.JwtUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AppAuthenticationManager(
    private val userService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: com.buran.core.auth.utils.JwtUtils,
) : AuthenticationManager {
    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val password = authentication.credentials.toString()
        val user = userService.loadUserByUsername(authentication.name)
        if (!passwordEncoder.matches(password, user.password)) {
            throw AuthError()
        }
        return UsernamePasswordAuthenticationToken(
            user.username,
            user.password,
            user.authorities,
        )
    }

    @Throws(AuthenticationException::class)
    fun authenticateFully(token: String): Authentication {
        val username = jwtUtils.getSubject(token)
        val user = userService.loadUserByUsername(username)
        val principal = User(user.username, "", user.authorities)
        return UsernamePasswordAuthenticationToken(
            principal,
            token,
            principal.authorities,
        )
    }

    @Throws(AuthenticationException::class)
    fun authenticateFirst(body: LoginInput): Authentication {
        val password = body.password
        val user = userService.loadUserByUsername(body.username)

        println(passwordEncoder.encode(password))
        println(user.password)
        if (!passwordEncoder.matches(password, user.password)) {
            throw AuthError()
        }
        return UsernamePasswordAuthenticationToken(
            user.username,
            user.password,
            user.authorities,
        )
    }
}
