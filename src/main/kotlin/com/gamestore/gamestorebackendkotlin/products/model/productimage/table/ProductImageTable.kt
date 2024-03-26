package com.gamestore.gamestorebackendkotlin.products.model.productimage.table

import com.gamestore.gamestorebackendkotlin.config.ExtendedLongIdTable
import com.gamestore.gamestorebackendkotlin.products.model.product.table.ProductTable
import org.jetbrains.exposed.sql.Column

object ProductImageTable : ExtendedLongIdTable(name = "product_image") {
    val image: Column<ByteArray> = binary(name = "image")
    val product =
        reference(
            "product",
            ProductTable,
        )
    val type: Column<String> = varchar(name = "type", length = 500)
}
