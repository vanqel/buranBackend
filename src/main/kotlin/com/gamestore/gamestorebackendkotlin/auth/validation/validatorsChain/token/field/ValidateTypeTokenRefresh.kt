package io.dtechs.core.auth.validation.validatorsChain.token.field

import io.dtechs.core.auth.errors.ValidationError
import io.dtechs.core.auth.filter.TokenProps
import io.dtechs.core.auth.utils.JwtUtils
import io.dtechs.core.auth.validation.ValidationProps
import io.dtechs.core.auth.validation.ValidatorInterface
import io.dtechs.core.extensions.isNull
import org.springframework.stereotype.Component

@Component
class ValidateTypeTokenRefresh(private val jwtUtils: JwtUtils) : ValidatorInterface<String> {

    override fun valid(arg: String?): ValidationError? {
        if (arg.isNull()) {
            return ValidationError(
                ValidationProps.VALIDATION_MSG_TOKEN,
                mapOf("Проверка токена на наличие" to "Введите токен"),
            )
        }
        val claims = jwtUtils.getBody(arg!!)
        if (claims[TokenProps.TYPE].toString() != TokenProps.REFRESH_TOKEN) {
            return ValidationError(
                ValidationProps.VALIDATION_MSG_TOKEN,
                mapOf("Проверка на тип" to "Токен не явлется типом Refresh"),
            )
        }
        return null
    }
}
