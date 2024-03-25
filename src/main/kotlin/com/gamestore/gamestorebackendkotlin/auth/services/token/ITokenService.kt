package com.gamestore.gamestorebackendkotlin.auth.services.token

import com.gamestore.gamestorebackendkotlin.auth.dto.token.TokenOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.token.TokenValidationOutput
import com.gamestore.gamestorebackendkotlin.extensions.Result
import jakarta.servlet.http.HttpServletResponse

interface ITokenService {
    fun validate(token: String): Result<TokenValidationOutput>

    fun refresh(
        response: HttpServletResponse,
        token: String,
    ): Result<TokenOutput>
}
