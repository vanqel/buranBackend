package com.gamestore.gamestorebackendkotlin.auth.dto.token

data class UserTempTokenOutput(
    val username: String,
    val temporary: String,
)
