package io.dtechs.core.auth.validation.validatorsChain.token.field

import io.dtechs.core.auth.errors.ValidationError
import io.dtechs.core.auth.utils.JwtUtils
import io.dtechs.core.auth.validation.ValidationProps
import io.dtechs.core.auth.validation.ValidatorInterface
import io.dtechs.core.extensions.isNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class ValidateExpirationToken(private val jwtUtils: JwtUtils) : ValidatorInterface<String> {
    override fun valid(arg: String?): ValidationError? {
        if (arg.isNull()) {
            return ValidationError(
                ValidationProps.VALIDATION_MSG_TOKEN,
                mapOf("Проверка токена на наличие" to "Введите токен"),
            )
        }
        val expirationToken = jwtUtils.getBody(arg!!).expiration

        if (expirationToken < Date(Date().time)) {
            return ValidationError(
                ValidationProps.VALIDATION_MSG_TOKEN,
                mapOf("Проверка токена" to "Токен истёк"),
            )
        }
        return null
    }
}
