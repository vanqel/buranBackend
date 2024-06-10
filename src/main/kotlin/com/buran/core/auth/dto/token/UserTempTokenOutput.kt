package com.buran.core.auth.dto.token

data class UserTempTokenOutput(
    val username: String,
    val temporary: String,
)
