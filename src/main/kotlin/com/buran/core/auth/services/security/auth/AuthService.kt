package com.buran.core.auth.services.security.auth

import com.buran.core.auth.config.SecurityProperties
import com.buran.core.auth.dto.authorization.AuthInput
import com.buran.core.auth.dto.login.LoginInput
import com.buran.core.auth.dto.token.TokenOutput
import com.buran.core.auth.dto.users.UserOutput
import com.buran.core.auth.dto.users.UserTokenOutput
import com.buran.core.auth.errors.AuthError
import com.buran.core.auth.repository.AuthRepository
import com.buran.core.auth.repository.IAuthRepository
import com.buran.core.auth.repository.IUsersRepository
import com.buran.core.auth.repository.UsersRepository
import com.buran.core.auth.services.security.app.AppAuthenticationManager
import com.buran.core.auth.services.token.TokenProvider
import com.buran.core.auth.utils.JwtUtils
import com.buran.core.extensions.Result
import com.buran.core.extensions.error
import com.buran.core.extensions.ok
import com.buran.core.extensions.setTokens
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val userRepository: IUsersRepository,
    private val appAuthenticationManager: AppAuthenticationManager,
    private val tokenProvider: TokenProvider,
    private val authRepository: IAuthRepository,
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
