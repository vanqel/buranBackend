package io.dtechs.core.auth.validation.validatorsChain.token.db

import io.dtechs.core.auth.errors.ValidationError
import io.dtechs.core.auth.repository.AuthRepository
import io.dtechs.core.auth.validation.ValidationProps
import io.dtechs.core.auth.validation.ValidatorInterface
import io.dtechs.core.extensions.isNull
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
            err = ValidationError(
                ValidationProps.VALIDATION_MSG_TOKEN,
                mapOf("Проверка токена на наличие" to "Токен не найден"),
            )
        }
        return err
    }
}
