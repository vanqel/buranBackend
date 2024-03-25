package io.dtechs.core.extensions

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.dtechs.core.auth.errors.CommonError

typealias Result<T> = Result<T, CommonError>

fun <T> Result.Companion.ok(value: T) = Ok(value)

fun <E : CommonError> Result.Companion.error(err: E) = Err(err)
