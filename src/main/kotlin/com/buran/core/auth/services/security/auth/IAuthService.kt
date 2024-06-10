package com.buran.core.auth.services.security.auth

import com.buran.core.auth.dto.login.LoginInput
import com.buran.core.auth.dto.users.UserTokenOutput
import com.buran.core.extensions.Result
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
