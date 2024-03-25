package io.dtechs.core.auth.validation.validatorsChain.users.field
import io.dtechs.core.auth.errors.ValidationError
import io.dtechs.core.auth.validation.ValidationProps
import io.dtechs.core.auth.validation.ValidatorInterface
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
            err = ValidationError(
                ValidationProps.VALIDATION_MSG_USER,
                mapOf("Проверка логина на длину" to "Логин должен быть длиннее 6 символов"),
            )
        }
        return err
    }
}
