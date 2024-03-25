package io.dtechs.core.auth.validation.validatorsChain.users.db

import io.dtechs.core.auth.errors.ValidationError
import io.dtechs.core.auth.repository.UsersRepository
import io.dtechs.core.auth.validation.ValidatorInterface
import org.springframework.stereotype.Component

@Component
class ValidateLoginDB(private val repository: UsersRepository) : ValidatorInterface<String> {

    override fun valid(arg: String?): ValidationError? {
        var err: ValidationError? = null
        if (arg == null) {
            return ValidationError(
                "Проверка логина пользователя",
                mapOf("Проверка на наличие" to "Введите логин"),
            )
        }
        if (repository.existUserByUsername(arg)) {
            err = ValidationError(
                "Проверка логина пользователя",
                mapOf("Проверка на уникальность" to "Логин уже занят"),
            )
        }
        return err
    }
}
