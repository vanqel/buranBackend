package com.buran.core.auth.dto.users

import com.buran.core.auth.dto.token.TokenOutput
import com.buran.core.auth.models.users.UserEntity

data class UserTokenOutput(
    val userOutput: UserOutput,
    val tokenOutput: TokenOutput,
) {
    constructor(u: UserEntity, t: TokenOutput) : this(UserOutput(u), t)
}
