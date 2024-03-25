package io.dtechs.core.auth.api.http

import com.github.michaelbull.result.getOrThrow
import io.dtechs.core.auth.dto.token.RefreshTokenInput
import io.dtechs.core.auth.dto.token.TokenOutput
import io.dtechs.core.auth.dto.token.TokenValidationOutput
import io.dtechs.core.auth.services.token.ITokenService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/token")
class TokenController(
    private val tokenService: ITokenService,
) {
    @GetMapping("validate")
    fun validate(
        @RequestParam(value = "token") token: String,
    ): ResponseEntity<TokenValidationOutput> {
        return ok(tokenService.validate(token).getOrThrow())
    }

    @GetMapping("validateUser")
    fun validateHeader(
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String,
    ): ResponseEntity<TokenValidationOutput> {
        return ok(tokenService.validate(token).getOrThrow())
    }

    @PostMapping("refresh")
    fun register(
        @RequestBody body: RefreshTokenInput,
        response: HttpServletResponse,
    ): ResponseEntity<TokenOutput> {
        return ok(tokenService.refresh(response, body.token).getOrThrow())
    }
}
