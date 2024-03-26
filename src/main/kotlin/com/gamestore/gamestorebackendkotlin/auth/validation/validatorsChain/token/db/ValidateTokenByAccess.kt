package com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.token.db

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.repository.AuthRepository
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidationProps
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidatorInterface
import com.gamestore.gamestorebackendkotlin.extensions.isNull
import org.springframework.stereotype.Component

@Component
class ValidateTokenByAccess(val authRepository: AuthRepository) : ValidatorInterface<String> {
    override fun valid(arg: String?): ValidationError? {
        var err: ValidationError? = null
        if (arg.isNull()) {
            return ValidationError(
                ValidationProps.VALIDATION_MSG_TOKEN,
                mapOf("Проверка токена на наличие" to "Введите токен"),
            )
        }
        if (!authRepository.existAccessToken(arg!!)) {
            err =
                ValidationError(
                    ValidationProps.VALIDATION_MSG_TOKEN,
                    mapOf("Проверка токена на наличие" to "Токен не найден"),
                )
        }
        return err
    }
}
