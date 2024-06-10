package com.buran.core.auth.dto.login

import com.buran.core.auth.dto.users.UserOutput

data class LoginOutput(
    val userDto: UserOutput,
    val accessToken: String,
    val refreshToken: String,
)
