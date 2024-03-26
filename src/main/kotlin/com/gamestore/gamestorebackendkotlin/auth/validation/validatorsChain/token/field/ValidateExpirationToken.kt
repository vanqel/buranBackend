package com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.token.field

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.utils.JwtUtils
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidationProps
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidatorInterface
import com.gamestore.gamestorebackendkotlin.extensions.isNull
import org.springframework.stereotype.Component
import java.util.Date

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
