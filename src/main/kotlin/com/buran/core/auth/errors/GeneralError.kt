package com.buran.core.auth.errors

class GeneralError(
    override val message: String,
    override val errorType: ErrorType = ErrorType.VALIDATION,
    override val errors: Map<String, String> = emptyMap(),
) : CommonError(message, errorType, errors)
