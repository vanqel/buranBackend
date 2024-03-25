package com.gamestore.gamestorebackendkotlin.products.model.productimage.table

import com.gamestore.gamestorebackendkotlin.auth.ExtendedLongIdTable
import com.gamestore.gamestorebackendkotlin.products.model.product.table.ProductTable
import org.jetbrains.exposed.sql.Column

object ProductImageTable : ExtendedLongIdTable(name = "user_auth") {
    val image: Column<ByteArray> = binary(name = "image")
    val link: Column<String> = varchar(name = "ling", length = 500)
    val product =
        reference(
            "product",
            ProductTable,
        )
    val type: Column<String> = varchar(name = "type", length = 500)
}
