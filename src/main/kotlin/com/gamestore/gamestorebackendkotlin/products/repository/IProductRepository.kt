package com.gamestore.gamestorebackendkotlin.products.repository

import com.gamestore.gamestorebackendkotlin.products.dto.product.ProductInputDTO
import com.gamestore.gamestorebackendkotlin.products.dto.product.ProductOutputDTO
import com.gamestore.gamestorebackendkotlin.products.dto.product.ProductUpdateImageInputDTO
import com.gamestore.gamestorebackendkotlin.products.dto.product.ProductUpdateInputDTO
import com.gamestore.gamestorebackendkotlin.products.model.product.ProductEntity

interface IProductRepository {
    fun save(body: ProductInputDTO): ProductOutputDTO?
    fun findByProductID(pid: Long): ProductEntity?
    fun findByCategory(category: String): List<ProductOutputDTO>
    fun update(body: ProductUpdateInputDTO): Boolean
    fun updateImage(body: ProductUpdateImageInputDTO): Boolean
    fun deleteProduct(pid: Long): Boolean
}