package com.buran.core.auth.repository

import com.buran.core.auth.dto.authorization.AuthInput
import com.buran.core.auth.dto.authorization.AuthOutput
import com.buran.core.auth.models.authorites.UserLoginEntity

interface IAuthRepository {
    fun save(inputAuth: AuthInput): AuthOutput?
    fun deleteByAccessToken(token: String): Boolean
    fun existAccessToken(token: String): Boolean
    fun existRefreshToken(token: String): Boolean
    fun findAuthByRefreshToken(refreshToken: String): UserLoginEntity?
}
