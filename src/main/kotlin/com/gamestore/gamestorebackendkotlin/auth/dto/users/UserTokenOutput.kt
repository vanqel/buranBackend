package com.gamestore.gamestorebackendkotlin.auth.dto.users

import com.gamestore.gamestorebackendkotlin.auth.dto.token.TokenOutput
import com.gamestore.gamestorebackendkotlin.auth.models.users.UserEntity

data class UserTokenOutput(
    val userOutput: UserOutput,
    val tokenOutput: TokenOutput,
) {
    constructor(u: UserEntity, t: TokenOutput) : this(UserOutput(u), t)
}
