package com.gamestore.gamestorebackendkotlin.products.repository

import com.gamestore.gamestorebackendkotlin.products.dto.product.ProductInputDTO
import com.gamestore.gamestorebackendkotlin.products.dto.product.ProductOutputDTO
import com.gamestore.gamestorebackendkotlin.products.dto.product.ProductUpdateImageInputDTO
import com.gamestore.gamestorebackendkotlin.products.dto.product.ProductUpdateInputDTO
import com.gamestore.gamestorebackendkotlin.products.model.product.ProductEntity
import com.gamestore.gamestorebackendkotlin.products.model.product.table.ProductTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class ProductRepository {
    fun save(body: ProductInputDTO): ProductOutputDTO? {
        val id =
            transaction {
                ProductTable.insertAndGetId {
                    it[logotype] = null
                    it[price] = body.price
                    it[description] = body.description
                    it[title] = body.title
                    it[category] = body.category
                    it[link] = body.link
                }
            }
        return ProductEntity.findById(id)?.toSimpleDTO()
    }

    fun findByProductID(pid: Long) = ProductEntity.findById(pid)

    fun findByCategory(category: String) = ProductEntity.find { ProductTable.category.eq(category) }.map { it.toSimpleDTO() }.toList()

    fun update(body: ProductUpdateInputDTO) =
        findByProductID(body.pid)?.let {
            it.title = body.product.title
            it.description = body.product.description
            it.price = body.product.price
            true
        } ?: false

    fun updateImage(body: ProductUpdateImageInputDTO) =
        findByProductID(body.pid)?.let {
            it.logotype = body.iid
            true
        } ?: false

    fun deleteProduct(pid: Long): Boolean = ProductTable.deleteWhere { id.eq(pid) } > 0
}
