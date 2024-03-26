package com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.db

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.repository.UsersRepository
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidationProps
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidatorInterface
import com.gamestore.gamestorebackendkotlin.extensions.isNull
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
            err =
                ValidationError(
                    ValidationProps.VALIDATION_MSG_USER,
                    mapOf("Проверка почты на уникальность" to "Почта уже занята"),
                )
        }
        return err
    }
}
