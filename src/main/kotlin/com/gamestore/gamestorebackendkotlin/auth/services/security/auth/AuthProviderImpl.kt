package io.dtechs.core.auth.services.security.auth

import io.dtechs.core.auth.filter.TokenProps
import io.dtechs.core.auth.models.authorites.UserLoginEntity
import io.dtechs.core.auth.models.authorites.table.UserLoginTable
import io.dtechs.core.auth.services.security.app.AppAuthenticationManager
import io.dtechs.core.auth.utils.JwtUtils
import io.dtechs.core.extensions.exists
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
            when (claims!![TokenProps.TYPE].toString()) {
                TokenProps.ACCESS_TOKEN -> {
                    if (UserLoginEntity.find { UserLoginTable.accesstoken eq token }.exists()) {
                        appAuthenticationManager.authenticateFully(token)
                    } else {
                        null
                    }
                }
                TokenProps.TEMPORARY_TOKEN -> {
                    val auth = appAuthenticationManager.authenticateTemp(token)
                    auth.isAuthenticated = false
                    auth
                }
                else -> null
            }
        } catch (e: Exception) {
            logger.warn(e.message!!)
            return null
        }
    }
}
