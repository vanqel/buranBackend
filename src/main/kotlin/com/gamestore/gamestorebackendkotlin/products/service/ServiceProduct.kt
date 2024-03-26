package com.gamestore.gamestorebackendkotlin.products.service
import com.gamestore.gamestorebackendkotlin.auth.errors.CommonError
import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.models.roles.table.ConstantRoles
import com.gamestore.gamestorebackendkotlin.extensions.*
import com.gamestore.gamestorebackendkotlin.products.dto.image.ProductImageInput
import com.gamestore.gamestorebackendkotlin.products.dto.product.*
import com.gamestore.gamestorebackendkotlin.products.model.product.ProductEntity
import com.gamestore.gamestorebackendkotlin.products.repository.ProductImageRepository
import com.gamestore.gamestorebackendkotlin.products.repository.ProductRepository
import com.github.michaelbull.result.Err
import jakarta.servlet.http.HttpServletResponse
import org.jetbrains.exposed.dao.id.EntityID
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class ServiceProduct(
    val productRepository: ProductRepository,
    val productImageRepository: ProductImageRepository,
) {
    fun verifyCreatorAdmin(): Boolean =
        SecurityContextHolder
            .getContext().authentication.authorities.filter { it.toString() == ConstantRoles.ADMIN }.isNotNull()

    fun allProduct(): Result<List<ProductOutputDTO>> = Result.ok(ProductEntity.all().map { it.toSimpleDTO() })

    fun getProductByCategory(category: String) =
        productRepository.findByCategory(category).let {
            Result.ok(it)
        }

    fun getProductByPid(pid: Long) =
        productRepository.findByProductID(pid)?.let {
            Result.ok(it.toSimpleDTO())
        } ?: Result.error(ValidationError("Продукт не найден"))

    fun addNewProduct(product: ProductInputDTO): Result<ProductOutputDTO> =
        try {
            if (verifyCreatorAdmin()) {
                productRepository.save(product)?.let {
                    Result.ok(it)
                } ?: throw Exception()
            } else {
                Result.error(ValidationError("Нет прав на добавление продуктов"))
            }
        } catch (e: Exception) {
            println(e)
            Result.error(ValidationError("Ошибка сохранения"))
        }

    fun updateProduct(product: ProductUpdateInputDTO): Result<ProductUpdateOutputDTO> =
        try {
            Result.ok(ProductUpdateOutputDTO(productRepository.update(product)))
        } catch (
            e: Exception,
        ) {
            Result.error(ValidationError("Ошибка обновления"))
        }

    fun deleteProduct(pid: Long): Result<ProductDeleteOutputDTO> =
        try {
            Result.ok(ProductDeleteOutputDTO(productRepository.deleteProduct(pid)))
        } catch (
            e: Exception,
        ) {
            Result.error(ValidationError("Ошибка удаления"))
        }

    fun updateProductImage(
        img: MultipartFile,
        pid: Long,
    ): Result<ProductUpdateOutputDTO> {
        val type = MediaType.valueOf(img.contentType!!)
        var err: Err<CommonError>? = null
        if (!type.isImage()) {
            err = Result.error(ValidationError("Файл загружен ошибочно"))
        }
        if (productRepository.findByProductID(pid).isNull()) {
            err = Result.error(ValidationError("Продукт не найден"))
        }
        if (!verifyCreatorAdmin()) {
            err = Result.error(ValidationError("У вас нет доступа"))
        }
        val imageData: ByteArray = img.inputStream.readAllBytes()

        return err ?: (
            try {
                val iid = productImageRepository.save(ProductImageInput(imageData, pid, type))
                productRepository.updateImage(ProductUpdateImageInputDTO(pid, iid))
                Result.ok(ProductUpdateOutputDTO(true))
            } catch (e: Exception) {
                Result.ok(ProductUpdateOutputDTO(false))
            }
        )
    }

    fun deleteProductImage(pid: Long): Result<ProductDeleteOutputDTO> {
        val entity: EntityID<Long>? = productRepository.findByProductID(pid)?.id
        var err: Err<CommonError>? = null
        if (!verifyCreatorAdmin()) {
            err = Result.error(ValidationError("У вас нет доступа"))
        }
        if (entity.isNull()) {
            err = Result.error(ValidationError("Продукт не найден"))
        }
        return err ?: (
            try {
                productImageRepository.deleteByProductId(entity!!)
                Result.ok(ProductDeleteOutputDTO(true))
            } catch (e: Exception) {
                Result.ok(ProductDeleteOutputDTO(false))
            }
        )
    }

    fun getImage(
        pid: Long,
        response: HttpServletResponse,
    ): Result<Pair<ByteArray, MediaType>> {
        val entity: EntityID<Long>? = productRepository.findByProductID(pid)?.id
        if (entity.isNull()) {
            return Result.error(ValidationError("Продукт не найден"))
        }
        return try {
            productImageRepository.getImageByPid(pid)?.let {
                Result.ok(Pair(it.image, MediaType.valueOf(it.type)))
            } ?: Result.error(ValidationError("Не найдено"))
        } catch (e: Exception) {
            Result.error(ValidationError("Ошибка при поиске"))
        }
    }
}
