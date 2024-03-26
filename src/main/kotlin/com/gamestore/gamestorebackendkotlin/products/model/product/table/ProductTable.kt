package com.gamestore.gamestorebackendkotlin.products.model.product.table

import com.gamestore.gamestorebackendkotlin.config.ExtendedLongIdTable
import com.gamestore.gamestorebackendkotlin.products.model.productimage.table.ProductImageTable
import org.jetbrains.exposed.sql.Column

object ProductTable : ExtendedLongIdTable(name = "product") {
    val title: Column<String> = varchar(name = "title", length = 500)
    val logotype =
        reference(
            "pid",
            ProductImageTable,
        ).nullable()
    val price: Column<Long> = long(name = "price")
    val description: Column<String?> = varchar(name = "description", length = 500).nullable()
    val category: Column<String> = varchar(name = "category", length = 100)
    val link: Column<String> = varchar(name = "linkDownload", length = 500)
}
