package io.dtechs.core.auth.errors

import io.dtechs.core.auth.exceptions.IExError

sealed class CommonError(
    override val message: String,
    override val errorType: ErrorType,
    override val errors: Map<String, String>,
) : IExError, RuntimeException()
