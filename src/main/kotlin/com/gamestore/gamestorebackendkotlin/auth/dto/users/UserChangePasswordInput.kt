package com.gamestore.gamestorebackendkotlin.auth.dto.users

data class UserChangePasswordInput(
    val username: String,
    val newPassword: String,
)
