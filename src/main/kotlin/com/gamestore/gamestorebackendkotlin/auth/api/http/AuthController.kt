package com.gamestore.gamestorebackendkotlin.auth.api.http

import com.gamestore.gamestorebackendkotlin.auth.dto.login.LoginInput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserTokenOutput
import com.gamestore.gamestorebackendkotlin.auth.services.security.auth.IAuthService
import com.gamestore.gamestorebackendkotlin.auth.utils.JwtUtils
import com.github.michaelbull.result.getOrThrow
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: IAuthService,
    private val jwtUtils: JwtUtils,
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
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String,
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): ResponseEntity<Nothing> {
        authService.logoutUser(token, response)
        request.session.invalidate()
        return ok().build()
    }

    @GetMapping("me")
    fun test(): ResponseEntity<Any> {
        return ok(jwtUtils.getBody(SecurityContextHolder.getContext().authentication.credentials.toString()))
    }
}
