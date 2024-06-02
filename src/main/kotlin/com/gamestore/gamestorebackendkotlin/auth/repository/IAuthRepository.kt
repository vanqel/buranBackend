package com.gamestore.gamestorebackendkotlin.auth.repository

import com.gamestore.gamestorebackendkotlin.auth.dto.authorization.AuthInput
import com.gamestore.gamestorebackendkotlin.auth.dto.authorization.AuthOutput
import com.gamestore.gamestorebackendkotlin.auth.models.authorites.UserLoginEntity

interface IAuthRepository {
    fun save(inputAuth: AuthInput): AuthOutput?
    fun deleteByAccessToken(token: String): Boolean
    fun existAccessToken(token: String): Boolean
    fun existRefreshToken(token: String): Boolean
    fun findAuthByRefreshToken(refreshToken: String): UserLoginEntity?
}
