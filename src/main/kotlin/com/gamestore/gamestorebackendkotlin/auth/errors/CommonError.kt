package com.gamestore.gamestorebackendkotlin.auth.errors

import com.gamestore.gamestorebackendkotlin.auth.exceptions.IExError

sealed class CommonError(
    override val message: String,
    override val errorType: ErrorType,
    override val errors: Map<String, String>,
) : IExError, RuntimeException()
