package com.gamestore.gamestorebackendkotlin.products.model.productimage

import com.gamestore.gamestorebackendkotlin.auth.ExtendedLongEntity
import com.gamestore.gamestorebackendkotlin.auth.models.users.table.UserTable
import com.gamestore.gamestorebackendkotlin.products.model.product.ProductEntity
import com.gamestore.gamestorebackendkotlin.products.model.productimage.table.ProductImageTable
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ProductEntity(id: EntityID<Long>) : ExtendedLongEntity(id, UserTable) {
    companion object : LongEntityClass<ProductEntity>(ProductImageTable)

    val image by ProductImageTable.image
    val product by ProductImageTable.product
    val link by ProductImageTable.link
    val type by ProductImageTable.type
}
