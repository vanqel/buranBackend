package com.gamestore.gamestorebackendkotlin.robokassa.service

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.repository.UsersRepository
import com.gamestore.gamestorebackendkotlin.extensions.Result
import com.gamestore.gamestorebackendkotlin.extensions.error
import com.gamestore.gamestorebackendkotlin.extensions.ok
import com.gamestore.gamestorebackendkotlin.products.repository.ProductRepository
import com.gamestore.gamestorebackendkotlin.robokassa.dto.OperationsOutput
import com.gamestore.gamestorebackendkotlin.robokassa.repository.KassaRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class OperationService(
    val kassaRepository: KassaRepository,
    val productRepository: ProductRepository,
    val usersRepository: UsersRepository,
) {
    fun getOperations(): Result<List<OperationsOutput>> {
        return SecurityContextHolder.getContext()?.let {
                authHolder ->
            authHolder.authentication.let { auth ->
                Result.ok(
                    kassaRepository.findByUser(usersRepository.findUserByUsername(auth.name)!!.id).mapNotNull {
                        it?.let {
                            val product = productRepository.findByProductID(it.product.value)!!
                            it.toDto(product.toSimpleDTO(), product.link)
                        }
                    },
                )
            }
        } ?: Result.error(ValidationError("Не найдено"))
    }
}
