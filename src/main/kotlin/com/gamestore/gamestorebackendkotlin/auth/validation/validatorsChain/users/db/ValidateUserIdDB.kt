package com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.db

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.repository.UsersRepository
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidationProps
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidatorInterface
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
            err =
                ValidationError(
                    ValidationProps.VALIDATION_MSG_USER,
                    mapOf("Поиск пользователя" to "Пользователь не найден"),
                )
        }
        return err
    }
}
