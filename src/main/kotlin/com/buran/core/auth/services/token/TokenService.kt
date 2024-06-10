package com.buran.core.auth.services.token

import com.buran.core.auth.config.SecurityProperties
import com.buran.core.auth.dto.authorization.AuthInput
import com.buran.core.auth.dto.token.TokenOutput
import com.buran.core.auth.dto.token.TokenValidationOutput
import com.buran.core.auth.errors.ValidationError
import com.buran.core.auth.models.users.UserEntity
import com.buran.core.auth.repository.IAuthRepository
import com.buran.core.extensions.Result
import com.buran.core.extensions.error
import com.buran.core.extensions.ok
import com.buran.core.extensions.setTokens
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val authRepository: IAuthRepository,
    private val tokenProvider: TokenProviderImpl,
) : ITokenService {
    override fun validate(token: String): Result<TokenValidationOutput> {
        val tokenVal =
            if (token.startsWith(SecurityProperties.TOKEN_PREFIX_ACCESS)) {
                token.replace(SecurityProperties.TOKEN_PREFIX_ACCESS, "")
            } else {
                token
            }

        return Result.ok(TokenValidationOutput(true))
    }

    override fun refresh(
        response: HttpServletResponse,
        token: String,
    ): Result<TokenOutput> {

        authRepository.findAuthByRefreshToken(token)?.let {
            val accessToken = tokenProvider.updatedAccessToken(it.access)
            val refreshToken = tokenProvider.updatedRefreshToken(it.refresh)

            response.setTokens(accessToken, refreshToken)

            authRepository.save(AuthInput(UserEntity(it.user), accessToken, refreshToken))

            return Result.ok(TokenOutput(accessToken, refreshToken))
        }

        return Result.error(
            ValidationError(
               "Валидация токена",
                mapOf("Обновление пары токенов" to "Произошла ошибка"),
            ),
        )
    }
}
