package com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.field
import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidationProps
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidatorInterface
import org.springframework.stereotype.Component

@Component
class ValidateLoginField : ValidatorInterface<String> {
    override fun valid(arg: String?): ValidationError? {
        var err: ValidationError? = null
        if (arg == null) {
            return ValidationError(
                ValidationProps.VALIDATION_MSG_USER,
                mapOf("Проверка на наличие логина" to "Введите логин"),
            )
        }

        if (arg.length < ValidationProps.LENGTH_USERNAME) {
            err =
                ValidationError(
                    ValidationProps.VALIDATION_MSG_USER,
                    mapOf("Проверка логина на длину" to "Логин должен быть длиннее 6 символов"),
                )
        }
        return err
    }
}
