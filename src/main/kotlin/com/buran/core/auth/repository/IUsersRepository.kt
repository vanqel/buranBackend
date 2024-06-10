package com.buran.core.auth.repository
import com.buran.core.auth.models.users.UserEntity

interface IUsersRepository {
    fun existUserByUsername(username: String): Boolean
    fun existUserByEmail(email: String): Boolean
    fun existUserById(id: Long): Boolean
    fun findUserByUsername(username: String): UserEntity?
    fun findUserById(id: Long): UserEntity?
    fun compareIdAndUsername(username: String, id: Long): Boolean
}
