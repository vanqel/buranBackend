package com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.token.field

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.filter.TokenProps
import com.gamestore.gamestorebackendkotlin.auth.utils.JwtUtils
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidationProps
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidatorInterface
import com.gamestore.gamestorebackendkotlin.extensions.isNull
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
