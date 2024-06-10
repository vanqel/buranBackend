package com.buran.core.auth.dto.authorization

import com.buran.core.auth.models.users.UserEntity

data class AuthInput(
    val userEntity: UserEntity,
    val accessToken: String,
    val refreshToken: String,
)
