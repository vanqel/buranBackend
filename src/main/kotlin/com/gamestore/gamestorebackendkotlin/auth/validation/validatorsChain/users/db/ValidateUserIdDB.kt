package io.dtechs.core.auth.validation.validatorsChain.users.db

import io.dtechs.core.auth.errors.ValidationError
import io.dtechs.core.auth.repository.UsersRepository
import io.dtechs.core.auth.validation.ValidationProps
import io.dtechs.core.auth.validation.ValidatorInterface
import org.springframework.stereotype.Component

@Component
class ValidateUserIdDB(private val repository: UsersRepository) : ValidatorInterface<String> {

    override fun valid(arg: String?): ValidationError? {
        var err: ValidationError? = null
        if (arg == null) {
            return ValidationError(
                ValidationProps.VALIDATION_MSG_USER,
                mapOf("Проверка id на наличие" to "Введите id"),
            )
        }
        if (!repository.existUserById(arg.toLong())) {
            err = ValidationError(
                ValidationProps.VALIDATION_MSG_USER,
                mapOf("Поиск пользователя" to "Пользователь не найден"),
            )
        }
        return err
    }
}
