package com.gamestore.gamestorebackendkotlin.products.model.product

import com.gamestore.gamestorebackendkotlin.config.ExtendedLongEntity
import com.gamestore.gamestorebackendkotlin.products.dto.product.ProductOutputDTO
import com.gamestore.gamestorebackendkotlin.products.model.product.table.ProductTable
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ProductEntity(id: EntityID<Long>) : ExtendedLongEntity(id, ProductTable) {
    companion object : LongEntityClass<ProductEntity>(ProductTable)

    var title by ProductTable.title
    var logotype by ProductTable.logotype
    var price by ProductTable.price
    var description by ProductTable.description
    var category by ProductTable.category
    var link by ProductTable.link

    fun toSimpleDTO(): ProductOutputDTO {
        return ProductOutputDTO(
            id = id.value,
            title = title,
            logotype?.let {
                "/product/" + id.value.toString() + "/image"
            },
            price = price,
            description = description,
            category = category,
        )
    }
}
