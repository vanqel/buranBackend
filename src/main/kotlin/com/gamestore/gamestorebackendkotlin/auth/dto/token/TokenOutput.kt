package com.gamestore.gamestorebackendkotlin.auth.dto.token

data class TokenOutput(
    val access: String,
    val refresh: String,
)
