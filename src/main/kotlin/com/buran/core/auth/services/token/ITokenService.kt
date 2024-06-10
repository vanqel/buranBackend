package com.buran.core.auth.services.token

import com.buran.core.auth.dto.token.TokenOutput
import com.buran.core.auth.dto.token.TokenValidationOutput
import com.buran.core.extensions.Result
import jakarta.servlet.http.HttpServletResponse

interface ITokenService {
    fun validate(token: String): Result<TokenValidationOutput>

    fun refresh(
        response: HttpServletResponse,
        token: String,
    ): Result<TokenOutput>
}
