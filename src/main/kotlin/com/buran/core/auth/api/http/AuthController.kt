package com.buran.core.auth.api.http

import com.buran.core.auth.dto.login.LoginInput
import com.buran.core.auth.dto.users.UserTokenOutput
import com.buran.core.auth.errors.AuthError
import com.buran.core.extensions.Result
import com.buran.core.auth.services.security.auth.IAuthService
import com.github.michaelbull.result.getOrThrow
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/auth")
class AuthController(
    private val authService: IAuthService,
    private val jwtUtils: com.buran.core.auth.utils.JwtUtils,
) {
    @PostMapping("login")
    fun loginfirst(
        @RequestBody body: LoginInput,
        response: HttpServletResponse,
    ): ResponseEntity<UserTokenOutput> {
        return ok(authService.authenticateFirst(body, response).getOrThrow())
    }

    @PostMapping("logout")
    fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): ResponseEntity<Nothing> {
        request.getHeader(HttpHeaders.AUTHORIZATION)?.let {
            authService.logoutUser(it.substring(7), response)
            request.session.invalidate()
            return ok().build()
        }?: throw AuthError()
    }

    @GetMapping("me")
    fun test(): ResponseEntity<Any> {
        return ok(jwtUtils.getBody(SecurityContextHolder.getContext().authentication.credentials.toString()))
    }
}
