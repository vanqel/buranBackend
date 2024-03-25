package com.gamestore.gamestorebackendkotlin.auth.services.user

import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserBlockOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserChangePasswordInput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserChangePasswordOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserCreateInput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserUpdateInput
import com.gamestore.gamestorebackendkotlin.extensions.Result

interface IUserService {
    fun registerUser(body: UserCreateInput): Result<UserOutput>

    fun updateUser(body: UserUpdateInput): Result<UserOutput>

    fun blockUser(
        username: String?,
        userId: Long?,
    ): Result<UserBlockOutput>

    fun updatePassword(body: UserChangePasswordInput): Result<UserChangePasswordOutput>
}
