package com.gamestore.gamestorebackendkotlin.auth.validation
import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError

interface ValidatorInterface<T> {
    fun valid(arg: T?): ValidationError?
}
