package com.gamestore.gamestorebackendkotlin.auth.repository

import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserBlockOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserChangePasswordInput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserChangePasswordOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserCreateInput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserUpdateInput
import com.gamestore.gamestorebackendkotlin.auth.models.users.UserEntity
import com.gamestore.gamestorebackendkotlin.auth.models.users.table.UserTable
import com.gamestore.gamestorebackendkotlin.auth.models.users.table.UsersRolesTable
import com.gamestore.gamestorebackendkotlin.extensions.exists
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class UsersRepository(
    val passwordEncoder: PasswordEncoder,
) {
    fun save(body: UserCreateInput): UserOutput {
        val newUserEntity =
            transaction {
                UserEntity.new {
                    username = body.username
                    password = passwordEncoder.encode(body.password)
                    phone = body.phone
                    email = body.email
                }
            }

        UsersRolesTable.insert {
            it[user] = newUserEntity.id
            it[role] = 2
        }

        return newUserEntity.toDTO()
    }

    fun existUserByUsername(username: String): Boolean =
        UserEntity.find {
            UserTable.username eq username
        }.exists()

    fun existUserByEmail(email: String): Boolean =
        UserEntity.find {
            UserTable.email eq email
        }.exists()

    fun existUserById(id: Long): Boolean =
        UserEntity.find {
            UserTable.id eq id
        }.exists()

    fun findUserByUsername(username: String): UserEntity? =
        UserEntity.find {
            UserTable.username eq username
        }.firstOrNull()

    fun findUserById(id: Long): UserEntity? =
        UserEntity.find {
            UserTable.id eq id
        }.firstOrNull()

    fun compareIdAndUsername(
        username: String,
        id: Long,
    ): Boolean =
        findUserById(id)
            ?.let {
                it.username == username
            } ?: false

    fun updateUser(body: UserUpdateInput): UserOutput {
        UserTable.update({ UserTable.id eq body.id }) {
            it[phone] = body.newPhone!!
            it[email] = body.newEmail!!
        }
        return UserEntity.findById(body.id)!!.toDTO()
    }

    fun updatePassword(body: UserChangePasswordInput): UserChangePasswordOutput {
        UserTable.update({ UserTable.username eq body.username }) {
            it[password] = passwordEncoder.encode(body.newPassword)
        }
        return UserChangePasswordOutput(true)
    }

    fun blockUser(id: Long): UserBlockOutput {
        val status = UserEntity.findById(id)!!.isBlocked
        UserTable.update({ UserTable.id eq id }) {
            it[isBlocked] = !status
        }
        return UserBlockOutput(!status)
    }
}
