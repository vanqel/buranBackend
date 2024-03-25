package com.gamestore.gamestorebackendkotlin.auth.services.token

import com.gamestore.gamestorebackendkotlin.auth.config.SecurityProperties
import com.gamestore.gamestorebackendkotlin.auth.dto.authorization.AuthInput
import com.gamestore.gamestorebackendkotlin.auth.dto.token.TokenOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.token.TokenValidationOutput
import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.models.users.UserEntity
import com.gamestore.gamestorebackendkotlin.auth.repository.AuthRepository
import com.gamestore.gamestorebackendkotlin.auth.validation.ChainValidate
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidationProps
import com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.token.db.ValidateTokenByAccess
import com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.token.db.ValidateTokenByRefresh
import com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.token.field.ValidateExpirationToken
import com.gamestore.gamestorebackendkotlin.extensions.Result
import com.gamestore.gamestorebackendkotlin.extensions.error
import com.gamestore.gamestorebackendkotlin.extensions.ok
import com.gamestore.gamestorebackendkotlin.extensions.setTokens
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val chainValidate: ChainValidate<String>,
    private val authRepository: AuthRepository,
    private val validateExpToken: ValidateExpirationToken,
    private val validateTokenByRefresh: ValidateTokenByRefresh,
    private val validateTokenByAccess: ValidateTokenByAccess,
    private val tokenProvider: TokenProviderImpl,
) : ITokenService {
    override fun validate(token: String): Result<TokenValidationOutput> {
        val tokenVal =
            if (token.startsWith(SecurityProperties.TOKEN_PREFIX_ACCESS)) {
                token.replace(SecurityProperties.TOKEN_PREFIX_ACCESS, "")
            } else {
                token
            }
        println(tokenVal)
        chainValidate.builder()
            .addChain(validateExpToken, tokenVal)
            .addChain(validateTokenByAccess, tokenVal)
            .findFirstException()?.let { return it }
        return Result.ok(TokenValidationOutput(true))
    }

    override fun refresh(
        response: HttpServletResponse,
        token: String,
    ): Result<TokenOutput> {
        chainValidate.builder()
            .addChain(validateExpToken, token)
            .addChain(validateTokenByRefresh, token)
            .findFirstException()?.let { return it }

        authRepository.findAuthByRefreshToken(token)?.let {
            val accessToken = tokenProvider.updatedAccessToken(it.access)
            val refreshToken = tokenProvider.updatedRefreshToken(it.refresh)

            response.setTokens(accessToken, refreshToken)

            authRepository.save(AuthInput(UserEntity(it.user), accessToken, refreshToken))

            return Result.ok(TokenOutput(accessToken, refreshToken))
        }

        return Result.error(
            ValidationError(
                ValidationProps.VALIDATION_MSG_TOKEN,
                mapOf("Обновление пары токенов" to "Произошла ошибка"),
            ),
        )
    }
}
