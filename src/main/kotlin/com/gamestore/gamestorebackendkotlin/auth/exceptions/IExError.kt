package com.gamestore.gamestorebackendkotlin.auth.exceptions

import com.gamestore.gamestorebackendkotlin.auth.errors.ErrorType

interface IExError {
    val message: String
    val errorType: ErrorType
    val errors: Map<String, String>

    fun hasErrors(): Boolean {
        return errors.isNotEmpty()
    }
}
