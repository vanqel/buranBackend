package com.gamestore.gamestorebackendkotlin.auth.dto.authorization

import com.gamestore.gamestorebackendkotlin.auth.models.users.UserEntity

data class AuthInput(
    val userEntity: UserEntity,
    val accessToken: String,
    val refreshToken: String,
)
