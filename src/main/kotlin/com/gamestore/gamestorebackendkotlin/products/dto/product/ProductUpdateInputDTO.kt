package com.gamestore.gamestorebackendkotlin.products.dto

data class ProductUpdateInputDTO(
    val pid: Long,
    val product: ProductInputDTO
)
