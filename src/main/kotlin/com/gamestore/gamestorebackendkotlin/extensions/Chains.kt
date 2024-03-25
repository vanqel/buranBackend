package io.dtechs.core.extensions

import com.github.michaelbull.result.Err
import io.dtechs.core.auth.errors.ValidationError

/**
 * Проверяет ошибки валидации, возвращает boolean
 */
fun Err<ValidationError>?.isValid(): Boolean {
    return this == null
}

fun Any?.isNotNull(): Boolean = this != null
fun Any?.isNull(): Boolean = this == null
