package io.dtechs.core.auth.dto.users

import com.github.michaelbull.result.Err
import io.dtechs.core.auth.errors.ValidationError
import io.dtechs.core.auth.validation.ChainValidate
import io.dtechs.core.auth.validation.validatorsChain.users.field.ValidateEmailField
import io.dtechs.core.auth.validation.validatorsChain.users.field.ValidateLoginField
import io.dtechs.core.auth.validation.validatorsChain.users.field.ValidatePasswordField
import io.dtechs.core.auth.validation.validatorsChain.users.field.ValidatePhoneRussiaField

data class UserCreateInput(
    val username: String,
    val password: String,
    val phone: String,
    val email: String,
    val snils: String,
    val refid: String,
    var roles: List<Long>,
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
