package com.gamestore.gamestorebackendkotlin.products.dto.product

data class ProductInputDTO(
    val title: String,
    val logotype: Long?,
    val price: Long,
    val description: String? = null,
    val category: String,
    val link: String,
)
