package io.dtechs.core.auth.dto.login

import io.dtechs.core.auth.dto.users.UserOutput

data class LoginOutput(
    val userDto: UserOutput,
    val accessToken: String,
    val refreshToken: String,
)
