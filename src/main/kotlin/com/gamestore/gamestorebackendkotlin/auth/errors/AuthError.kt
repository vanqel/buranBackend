package com.gamestore.gamestorebackendkotlin.auth.errors

class AuthError(
    override var message: String = "Unauthorized",
    override val errorType: ErrorType = ErrorType.AUTH,
    override val errors: Map<String, String> = emptyMap(),
) : CommonError(message, errorType, errors) {
    companion object {
        fun default() = AuthError()
    }
}
