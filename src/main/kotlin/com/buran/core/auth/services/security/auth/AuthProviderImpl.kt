package com.buran.core.auth.services.security.auth

import com.buran.core.auth.filter.TokenProps
import com.buran.core.auth.models.authorites.UserLoginEntity
import com.buran.core.auth.models.authorites.table.UserLoginTable
import com.buran.core.auth.services.security.app.AppAuthenticationManager
import com.buran.core.auth.utils.JwtUtils
import com.buran.core.extensions.exists
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AuthProviderImpl(
    private val appAuthenticationManager: AppAuthenticationManager,
    private val jwtUtils: JwtUtils,
) : AuthProvider {
    companion object : Logging

    @Transactional
    override fun getAuthentication(token: String): Authentication? {
        return try {
            val claims = jwtUtils.getBody(token)
            if (claims[TokenProps.TYPE] == TokenProps.ACCESS_TOKEN &&
                UserLoginEntity.find { UserLoginTable.accesstoken eq token }.exists()
            ) {
                appAuthenticationManager.authenticateFully(token)
            } else {
                null
            }
        } catch (e: Exception) {
            logger.warn(e.message!!)
            return null
        }
    }
}
