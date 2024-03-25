package io.dtechs.core.auth.repository

import io.dtechs.core.auth.config.SecurityProperties
import io.dtechs.core.auth.dto.authorization.AuthInput
import io.dtechs.core.auth.dto.authorization.AuthOutput
import io.dtechs.core.auth.models.authorites.UserLoginEntity
import io.dtechs.core.auth.models.authorites.table.UserLoginTable
import io.dtechs.core.extensions.exists
import io.dtechs.core.extensions.selectAllNotDeleted
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException

@Repository
@Transactional
class AuthRepository {
    fun save(inputAuth: AuthInput): AuthOutput? {
        if (UserLoginEntity.find { UserLoginTable.user eq inputAuth.userEntity.id }.exists()) {
            UserLoginTable.deleteWhere { user eq inputAuth.userEntity.id.value }
        }
        UserLoginTable.insertAndGetId {
            it[user] = inputAuth.userEntity.id
            it[accesstoken] = inputAuth.accessToken
            it[refreshtoken] = inputAuth.refreshToken
        }
        return AuthOutput(inputAuth.userEntity.id.value, inputAuth.accessToken, inputAuth.refreshToken)
    }

    fun deleteByAccessToken(token: String): Boolean {
        val tokenEdited = token.substringAfter(SecurityProperties.TOKEN_PREFIX_ACCESS)
        return try {
            UserLoginTable.deleteWhere { accesstoken.eq(tokenEdited) }
            true
        } catch (e: SQLException) {
            false
        }
    }

    fun existAccessToken(token: String): Boolean =
        UserLoginTable.selectAllNotDeleted()
            .andWhere { UserLoginTable.accesstoken eq token }
            .exists()

    fun existRefreshToken(token: String): Boolean =
        UserLoginTable.selectAllNotDeleted()
            .andWhere { UserLoginTable.refreshtoken eq token }
            .exists()

    fun findAuthByRefreshToken(refreshToken: String): UserLoginEntity? =
        UserLoginEntity.find { UserLoginTable.refreshtoken eq refreshToken }.firstOrNull()
}
