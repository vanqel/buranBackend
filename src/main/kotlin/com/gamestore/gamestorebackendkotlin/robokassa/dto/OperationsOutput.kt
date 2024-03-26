package com.gamestore.gamestorebackendkotlin.robokassa.dto

import com.gamestore.gamestorebackendkotlin.products.dto.product.ProductOutputDTO

data class OperationsOutput(
    val invID: Int,
    val product: ProductOutputDTO,
    val status: Boolean?,
    val link: String?,
)
