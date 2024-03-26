package com.gamestore.gamestorebackendkotlin.products.model.productimage

import com.gamestore.gamestorebackendkotlin.config.ExtendedLongEntity
import com.gamestore.gamestorebackendkotlin.products.model.productimage.table.ProductImageTable
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ProductImageEntity(id: EntityID<Long>) : ExtendedLongEntity(id, ProductImageTable) {
    companion object : LongEntityClass<ProductImageEntity>(ProductImageTable)

    var image by ProductImageTable.image
    var product by ProductImageTable.product
    var type by ProductImageTable.type
}
