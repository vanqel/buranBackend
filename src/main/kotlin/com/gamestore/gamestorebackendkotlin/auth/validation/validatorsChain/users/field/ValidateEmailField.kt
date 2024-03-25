package io.dtechs.core.auth.validation.validatorsChain.users.field

import io.dtechs.core.auth.errors.ValidationError
import io.dtechs.core.auth.validation.ValidationProps
import io.dtechs.core.auth.validation.ValidatorInterface
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
            err = ValidationError(
                ValidationProps.VALIDATION_MSG_USER,
                mapOf("Проверка на соответствие" to "Введите почту правильно"),
            )
        }
        return err
    }
}
