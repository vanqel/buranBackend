package io.dtechs.core.auth.validation.validatorsChain.users.db

import io.dtechs.core.auth.errors.ValidationError
import io.dtechs.core.auth.repository.UsersRepository
import io.dtechs.core.auth.validation.ValidationProps
import io.dtechs.core.auth.validation.ValidatorInterface
import io.dtechs.core.extensions.isNull
import org.springframework.stereotype.Component

@Component
class ValidateEmailDB(private val repository: UsersRepository) : ValidatorInterface<String> {

    override fun valid(arg: String?): ValidationError? {
        var err: ValidationError? = null
        if (arg.isNull()) {
            return ValidationError(
                ValidationProps.VALIDATION_MSG_USER,
                mapOf("Проверка почты на наличие" to "Введите почту"),
            )
        }
        if (repository.existUserByEmail(arg!!)) {
            err = ValidationError(
                ValidationProps.VALIDATION_MSG_USER,
                mapOf("Проверка почты на уникальность" to "Почта уже занята"),
            )
        }
        return err
    }
}
