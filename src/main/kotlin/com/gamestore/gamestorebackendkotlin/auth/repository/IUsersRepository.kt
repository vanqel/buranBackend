package com.gamestore.gamestorebackendkotlin.auth.repository
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserBlockOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserChangePasswordInput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserChangePasswordOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserCreateInput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserUpdateInput
import com.gamestore.gamestorebackendkotlin.auth.models.users.UserEntity

interface IUsersRepository {
    fun save(body: UserCreateInput): UserOutput
    fun existUserByUsername(username: String): Boolean
    fun existUserByEmail(email: String): Boolean
    fun existUserById(id: Long): Boolean
    fun findUserByUsername(username: String): UserEntity?
    fun findUserById(id: Long): UserEntity?
    fun compareIdAndUsername(username: String, id: Long): Boolean
    fun updateUser(body: UserUpdateInput): UserOutput
    fun updatePassword(body: UserChangePasswordInput): UserChangePasswordOutput
    fun blockUser(id: Long): UserBlockOutput
}