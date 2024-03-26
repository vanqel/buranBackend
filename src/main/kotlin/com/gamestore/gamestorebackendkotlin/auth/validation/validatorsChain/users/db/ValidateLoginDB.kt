package com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.db

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.repository.UsersRepository
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidatorInterface
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
            err =
                ValidationError(
                    "Проверка логина пользователя",
                    mapOf("Проверка на уникальность" to "Логин уже занят"),
                )
        }
        return err
    }
}
