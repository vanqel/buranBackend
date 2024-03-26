package com.gamestore.gamestorebackendkotlin.auth.dto.login

import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserOutput

data class LoginOutput(
    val userDto: UserOutput,
    val accessToken: String,
    val refreshToken: String,
)
