package com.gamestore.gamestorebackendkotlin.auth.dto.users

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.validation.ChainValidate
import com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.field.ValidateEmailField
import com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.field.ValidatePhoneRussiaField
import com.github.michaelbull.result.Err

data class UserUpdateInput(
    val id: Long,
    val newPhone: String? = null,
    val newEmail: String? = null,
) {
    fun validate(): Err<ValidationError>? {
        return ChainValidate<String>()
            .builder()
            .addChain(ValidateEmailField(), newEmail)
            .addChain(ValidatePhoneRussiaField(), newPhone)
            .findAllException()
    }
}
