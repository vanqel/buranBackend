package com.buran.core.auth.repository


import com.buran.core.auth.models.users.UserEntity
import com.buran.core.auth.models.users.table.UserTable
import com.buran.core.extensions.exists
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class UsersRepository(
    val passwordEncoder: PasswordEncoder,
): IUsersRepository {
    

    override fun existUserByUsername(username: String): Boolean =
        UserEntity.find {
            UserTable.username eq username
        }.exists()

    override fun existUserByEmail(email: String): Boolean =
        UserEntity.find {
            UserTable.email eq email
        }.exists()

    override fun existUserById(id: Long): Boolean =
        UserEntity.find {
            UserTable.id eq id
        }.exists()

    override fun findUserByUsername(username: String): UserEntity? =
        UserEntity.find {
            UserTable.username eq username
        }.firstOrNull()

    override fun findUserById(id: Long): UserEntity? =
        UserEntity.find {
            UserTable.id eq id
        }.firstOrNull()

    override fun compareIdAndUsername(
        username: String,
        id: Long,
    ): Boolean =
        findUserById(id)
            ?.let {
                it.username == username
            } ?: false

}
