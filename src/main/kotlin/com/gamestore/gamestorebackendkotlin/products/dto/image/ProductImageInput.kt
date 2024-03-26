package com.gamestore.gamestorebackendkotlin.products.dto.image

import org.springframework.http.MediaType

data class ProductImageInput(
    var image: ByteArray,
    var product: Long,
    var type: MediaType,
)
