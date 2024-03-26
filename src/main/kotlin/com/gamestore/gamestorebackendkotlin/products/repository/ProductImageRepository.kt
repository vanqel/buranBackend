package com.gamestore.gamestorebackendkotlin.products.repository

import com.gamestore.gamestorebackendkotlin.products.dto.image.ProductImageInput
import com.gamestore.gamestorebackendkotlin.products.model.productimage.ProductImageEntity
import com.gamestore.gamestorebackendkotlin.products.model.productimage.table.ProductImageTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class ProductImageRepository {
    fun save(body: ProductImageInput) =
        ProductImageTable.insertAndGetId {
            it[image] = body.image
            it[type] = body.type.toString()
            it[product] = body.product
        }

    fun deleteByProductId(pid: EntityID<Long>) = ProductImageTable.deleteWhere { product.eq(pid) } > 0

    fun getImageByPid(pid: Long): ProductImageEntity? = ProductImageEntity.find(ProductImageTable.product.eq(pid)).firstOrNull()
}
