package com.gamestore.gamestorebackendkotlin.products.dto.product

data class ProductUpdateInputDTO(
    val pid: Long,
    val product: ProductInputDTO,
)
