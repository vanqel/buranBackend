package com.gamestore.gamestorebackendkotlin.products.dto.image

import org.springframework.http.MediaType

data class ProductImageOutput(
    val image: ByteArray,
    val type: MediaType,
)
