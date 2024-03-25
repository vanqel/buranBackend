package io.dtechs.core.auth.dto.token

data class TokenOutput(
    val access: String,
    val refresh: String,
)
