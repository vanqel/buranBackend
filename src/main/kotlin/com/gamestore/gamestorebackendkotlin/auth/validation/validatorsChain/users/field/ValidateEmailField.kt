package com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.field

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidationProps
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidatorInterface
import org.springframework.stereotype.Component

@Component
class ValidateEmailField : ValidatorInterface<String> {
    override fun valid(arg: String?): ValidationError? {
        var err: ValidationError? = null
        if (arg == null) {
            return ValidationError(
                ValidationProps.VALIDATION_MSG_USER,
                mapOf("Проверка на наличие" to "Введите почту"),
            )
        }
        if (ValidationProps.EmailCase.find(arg) == null) {
            err =
                ValidationError(
                    ValidationProps.VALIDATION_MSG_USER,
                    mapOf("Проверка на соответствие" to "Введите почту правильно"),
                )
        }
        return err
    }
}
