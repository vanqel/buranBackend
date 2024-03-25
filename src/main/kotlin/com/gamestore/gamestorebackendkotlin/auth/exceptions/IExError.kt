package io.dtechs.core.auth.exceptions

import io.dtechs.core.auth.errors.ErrorType

interface IExError {
    val message: String
    val errorType: ErrorType
    val errors: Map<String, String>

    fun hasErrors(): Boolean {
        return errors.isNotEmpty()
    }
}
