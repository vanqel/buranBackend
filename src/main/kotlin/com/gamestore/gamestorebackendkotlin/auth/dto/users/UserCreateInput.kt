package com.gamestore.gamestorebackendkotlin.auth.dto.users

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.validation.ChainValidate
import com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.field.ValidateEmailField
import com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.field.ValidateLoginField
import com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.field.ValidatePasswordField
import com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.field.ValidatePhoneRussiaField
import com.github.michaelbull.result.Err

data class UserCreateInput(
    val username: String,
    val password: String,
    val phone: String,
    val email: String,
) {
    fun validate(): Err<ValidationError>? {
        return ChainValidate<String>()
            .builder()
            .addChain(ValidateLoginField(), username)
            .addChain(ValidateEmailField(), email)
            .addChain(ValidatePasswordField(), password)
            .addChain(ValidatePhoneRussiaField(), phone)
            .findAllException()
    }
}
