package com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.field

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidationProps
import com.gamestore.gamestorebackendkotlin.auth.validation.ValidatorInterface

class ValidatePhoneRussiaField : ValidatorInterface<String> {
    override fun valid(arg: String?): ValidationError? {
        val errors = mutableMapOf<String, String>()
        var err: ValidationError? = null
        if (arg == null) {
            errors["Проверка номера"] = "Введите телефон"
            return ValidationError(ValidationProps.VALIDATION_MSG_USER, errors)
        }
        if (arg.length < ValidationProps.LENGTH_PHONE) {
            errors["Длина номера"] = "Номер должен быть длиннее 11 символов"
        }
        if (ValidationProps.PhoneCaseRU.find(arg) == null) {
            errors["Код страны"] = "Номер должен начинаться с +7 или 8"
        }
        if (errors != emptyMap<String, String>()) {
            err = ValidationError(ValidationProps.VALIDATION_MSG_USER, errors)
        }
        return err
    }
}
