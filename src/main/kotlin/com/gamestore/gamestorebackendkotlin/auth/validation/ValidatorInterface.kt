package io.dtechs.core.auth.validation
import io.dtechs.core.auth.errors.ValidationError

interface ValidatorInterface<T> {
    fun valid(arg: T?): ValidationError?
}
