package com.buran.core.auth.errors

import com.buran.core.auth.exceptions.IExError


sealed class CommonError(
    override val message: String,
    override val errorType: ErrorType,
    override val errors: Map<String, String>,
) : IExError, RuntimeException()
