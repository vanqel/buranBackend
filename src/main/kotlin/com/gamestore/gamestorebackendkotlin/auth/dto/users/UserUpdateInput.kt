package io.dtechs.core.auth.dto.users

import com.github.michaelbull.result.Err
import io.dtechs.core.auth.errors.ValidationError
import io.dtechs.core.auth.validation.ChainValidate
import io.dtechs.core.auth.validation.validatorsChain.users.field.ValidateEmailField
import io.dtechs.core.auth.validation.validatorsChain.users.field.ValidatePhoneRussiaField

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
