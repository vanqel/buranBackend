package com.gamestore.gamestorebackendkotlin.auth.services.security.auth

import com.gamestore.gamestorebackendkotlin.auth.dto.login.LoginInput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserTokenOutput
import com.gamestore.gamestorebackendkotlin.extensions.Result
import jakarta.servlet.http.HttpServletResponse

interface IAuthService {
    fun authenticateFirst(
        body: LoginInput,
        response: HttpServletResponse,
    ): Result<UserTokenOutput>

    fun logoutUser(
        token: String,
        response: HttpServletResponse,
    )
}
