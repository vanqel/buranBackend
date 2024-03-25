package com.gamestore.gamestorebackendkotlin.products.dto.product

data class ProductOutputDTO(
    val id: Long,
    val title: String,
    val link: String?,
    val price: Long,
    val description: String?,
    val category: String,
)
