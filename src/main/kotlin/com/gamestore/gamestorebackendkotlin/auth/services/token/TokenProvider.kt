package io.dtechs.core.auth.services.token

import io.dtechs.core.auth.dto.authorization.FrmrInfoInput
import org.springframework.security.core.Authentication

interface TokenProvider {
    fun createAccessToken(authentication: Authentication, frmrInfo: FrmrInfoInput): String
    fun createRefreshToken(authentication: Authentication): String
    fun createTimedToken(authentication: Authentication): String

    fun updatedAccessToken(token: String): String
    fun updatedRefreshToken(token: String): String
}
