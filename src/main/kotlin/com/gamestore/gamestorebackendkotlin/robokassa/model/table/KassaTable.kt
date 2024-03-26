package com.gamestore.gamestorebackendkotlin.robokassa.model.table

import com.gamestore.gamestorebackendkotlin.auth.models.users.table.UserTable
import com.gamestore.gamestorebackendkotlin.config.ExtendedLongIdTable
import com.gamestore.gamestorebackendkotlin.products.model.product.table.ProductTable
import org.jetbrains.exposed.sql.Column

object KassaTable : ExtendedLongIdTable(name = "kassa_table") {
    val user =
        reference(
            "user",
            UserTable,
        )

    val individualID: Column<Int> = integer("invoiceID")

    val product =
        reference(
            "product",
            ProductTable,
        )

    val status: Column<Boolean?> = bool("succes").nullable().default(null)
}
