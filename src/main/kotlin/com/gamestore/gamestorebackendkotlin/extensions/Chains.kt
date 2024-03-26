package com.gamestore.gamestorebackendkotlin.extensions

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.github.michaelbull.result.Err

/**
 * Проверяет ошибки валидации, возвращает boolean
 */
fun Err<ValidationError>?.isValid(): Boolean {
    return this == null
}

fun Any?.isNotNull(): Boolean = this != null

fun Any?.isNull(): Boolean = this == null
