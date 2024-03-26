package com.gamestore.gamestorebackendkotlin.auth.errors

class ValidationError(
    override val message: String,
    override val errors: Map<String, String> = emptyMap(),
    override val errorType: ErrorType = ErrorType.VALIDATION,
) : CommonError(message, errorType, errors)
