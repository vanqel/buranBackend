package com.gamestore.gamestorebackendkotlin.products.repository

import com.gamestore.gamestorebackendkotlin.products.dto.image.ProductImageInput
import com.gamestore.gamestorebackendkotlin.products.model.productimage.ProductImageEntity
import org.jetbrains.exposed.dao.id.EntityID

interface IProductImageRepository {
    fun save(body: ProductImageInput): EntityID<Long>
    fun deleteByProductId(pid: EntityID<Long>): Boolean
    fun getImageByPid(pid: Long): ProductImageEntity?
}