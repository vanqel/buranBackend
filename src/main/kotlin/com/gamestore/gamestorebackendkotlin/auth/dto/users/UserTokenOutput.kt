package io.dtechs.core.auth.dto.users

import io.dtechs.core.auth.dto.token.TokenOutput
import io.dtechs.core.auth.models.users.UserEntity

data class UserTokenOutput(
    val userOutput: UserOutput,
    val tokenOutput: TokenOutput,
) {
    constructor(u: UserEntity, t: TokenOutput) : this(UserOutput(u), t)
}
