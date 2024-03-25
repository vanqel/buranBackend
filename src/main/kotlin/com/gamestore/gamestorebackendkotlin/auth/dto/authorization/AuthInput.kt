package io.dtechs.core.auth.dto.authorization

import io.dtechs.core.auth.models.users.UserEntity

data class AuthInput(
    val userEntity: UserEntity,
    val accessToken: String,
    val refreshToken: String,
)
