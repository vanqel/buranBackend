package com.gamestore.gamestorebackendkotlin.auth.validation

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.extensions.Result
import com.gamestore.gamestorebackendkotlin.extensions.error
import com.github.michaelbull.result.Err
import org.springframework.stereotype.Component

@Component
class ChainValidate<T> {
    /**
     * Лист хранящий в себе валидаторы
     */
    private var chains: List<Pair<ValidatorInterface<T>, T?>> = mutableListOf()

    /**
     * Создание валдитора с пустым списком, что бы не было коллизий
     */
    fun builder(): ChainValidate<T> {
        chains = mutableListOf()
        return this
    }

    /**
     * Добавление нового валидатора в конец списка
     */
    fun addChain(
        validator: ValidatorInterface<T>,
        value: T?,
    ): ChainValidate<T> {
        chains.addLast(validator to value)
        return this
    }

    /**
     * Возвращает первую попавшуюся ошибку валидации, если её нету, возвращает null
     */
    fun findFirstException(): Err<ValidationError>? {
        chains.asSequence()
            .mapNotNull { it.first.valid(it.second) }
            .firstOrNull()
            ?.let {
                return Result.error(it)
            } ?: return null
    }

    /**
     * Возвращает все ошибки валидации, если её нету, возвращает null
     */
    fun findAllException(): Err<ValidationError>? {
        val err = mutableMapOf<String, String>()
        var header: String? = null
        chains.asSequence()
            .forEach { r ->
                r.first.valid(r.second)
                    ?.let {
                        header = it.message
                        err.putAll(it.errors)
                    }
            }
        return if (err.isNotEmpty() && header != null) {
            Result.error(ValidationError(header!!, err))
        } else {
            null
        }
    }
}
