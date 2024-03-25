package com.gamestore.gamestorebackendkotlin.products.dto

import com.gamestore.gamestorebackendkotlin.products.model.product.table.ProductTable
import org.jetbrains.exposed.sql.Column

data class ProductInputDTO(
        var id: Long?,
        val title : String,
        val logotype: Long?,
        val price: Long,
        val description: String? = null
)

