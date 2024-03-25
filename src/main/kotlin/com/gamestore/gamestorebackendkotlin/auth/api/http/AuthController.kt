package io.dtechs.core.auth.api.http

import com.github.michaelbull.result.getOrThrow
import io.dtechs.core.auth.dto.authorization.FrmrInfoInput
import io.dtechs.core.auth.dto.login.LoginInput
import io.dtechs.core.auth.dto.token.UserTempTokenOutput
import io.dtechs.core.auth.dto.users.UserTokenOutput
import io.dtechs.core.auth.services.security.auth.IAuthService
import io.dtechs.core.auth.utils.JwtUtils
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
    ): ResponseEntity<UserTempTokenOutput> {
        return ok(authService.authenticateFirst(body, response).getOrThrow())
    }

    @PostMapping("setRole")
    fun loginfull(
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String,
        @RequestBody input: FrmrInfoInput,
        response: HttpServletResponse,
    ): ResponseEntity<UserTokenOutput> {
        return ok(
            authService.authenticateFully(token, response, input).getOrThrow(),
        )
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
