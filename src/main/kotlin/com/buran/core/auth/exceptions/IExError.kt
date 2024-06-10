package com.buran.core.auth.exceptions

import com.buran.core.auth.errors.ErrorType

interface IExError {
    val message: String
    val errorType: ErrorType
    val errors: Map<String, String>

    fun hasErrors(): Boolean {
        return errors.isNotEmpty()
    }
}
