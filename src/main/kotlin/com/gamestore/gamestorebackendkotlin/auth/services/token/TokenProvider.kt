package com.gamestore.gamestorebackendkotlin.auth.services.token

import org.springframework.security.core.Authentication

interface TokenProvider {
    fun createAccessToken(authentication: Authentication): String

    fun createRefreshToken(authentication: Authentication): String

    fun updatedAccessToken(token: String): String

    fun updatedRefreshToken(token: String): String
}
