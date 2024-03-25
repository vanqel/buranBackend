package io.dtechs.core.auth.dto.users

data class UserChangePasswordInput(
    val username: String,
    val newPassword: String,
)
