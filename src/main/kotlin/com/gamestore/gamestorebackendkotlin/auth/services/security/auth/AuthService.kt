package com.gamestore.gamestorebackendkotlin.auth.services.security.auth

import com.gamestore.gamestorebackendkotlin.auth.config.SecurityProperties
import com.gamestore.gamestorebackendkotlin.auth.dto.authorization.AuthInput
import com.gamestore.gamestorebackendkotlin.auth.dto.login.LoginInput
import com.gamestore.gamestorebackendkotlin.auth.dto.token.TokenOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserTokenOutput
import com.gamestore.gamestorebackendkotlin.auth.errors.AuthError
import com.gamestore.gamestorebackendkotlin.auth.repository.AuthRepository
import com.gamestore.gamestorebackendkotlin.auth.repository.UsersRepository
import com.gamestore.gamestorebackendkotlin.auth.services.security.app.AppAuthenticationManager
import com.gamestore.gamestorebackendkotlin.auth.services.token.TokenProvider
import com.gamestore.gamestorebackendkotlin.auth.utils.JwtUtils
import com.gamestore.gamestorebackendkotlin.extensions.Result
import com.gamestore.gamestorebackendkotlin.extensions.error
import com.gamestore.gamestorebackendkotlin.extensions.ok
import com.gamestore.gamestorebackendkotlin.extensions.setTokens
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val userRepository: UsersRepository,
    private val appAuthenticationManager: AppAuthenticationManager,
    private val tokenProvider: TokenProvider,
    private val authRepository: AuthRepository,
    private val jwtUtils: JwtUtils,
) : IAuthService {
    override fun authenticateFirst(
        body: LoginInput,
        response: HttpServletResponse,
    ): Result<UserTokenOutput> {
        var authentication =
            try {
                appAuthenticationManager.authenticateFirst(body)
            } catch (e: BadCredentialsException) {
                return Result.error(AuthError())
            }

        val accessToken = tokenProvider.createAccessToken(authentication)
        val refreshToken = tokenProvider.createRefreshToken(authentication)

        authentication =
            try {
                appAuthenticationManager.authenticateFully(accessToken)
            } catch (e: BadCredentialsException) {
                return Result.error(AuthError())
            }

        SecurityContextHolder.getContext().authentication = authentication

        response.setTokens(accessToken, refreshToken)

        userRepository.findUserByUsername(authentication.name)?.let {
            authRepository.save(AuthInput(it, accessToken, refreshToken))
            return Result.ok(UserTokenOutput(UserOutput(it), TokenOutput(accessToken, refreshToken)))
        }
        return Result.error(AuthError())
    }

    override fun logoutUser(
        token: String,
        response: HttpServletResponse,
    ) {
        response.setHeader(SecurityProperties.HEADER_STRING, "")
        authRepository.deleteByAccessToken(token)
        SecurityContextHolder.getContext().authentication = null
    }
}
