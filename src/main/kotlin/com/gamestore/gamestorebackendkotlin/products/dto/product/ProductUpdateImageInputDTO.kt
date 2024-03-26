package com.gamestore.gamestorebackendkotlin.products.dto.product

import org.jetbrains.exposed.dao.id.EntityID

data class ProductUpdateImageInputDTO(
    val pid: Long,
    val iid: EntityID<Long>,
)
