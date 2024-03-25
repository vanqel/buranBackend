package com.gamestore.gamestorebackendkotlin.products.api.http

import com.gamestore.gamestorebackendkotlin.products.dto.product.*
import com.gamestore.gamestorebackendkotlin.products.service.ServiceProduct
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getOrThrow
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("product")
class ProductController(
    val serviceProduct: ServiceProduct,
) {
    @GetMapping
    fun allProduct(): ResponseEntity<List<ProductOutputDTO>> = ResponseEntity.ok(serviceProduct.allProduct().getOrThrow())

    @GetMapping("category/{category}")
    fun allByCategoryProduct(
        @PathVariable category: String,
    ): ResponseEntity<List<ProductOutputDTO>> = ResponseEntity.ok(serviceProduct.getProductByCategory(category).getOrThrow())

    @PostMapping
    fun addNewProduct(
        @RequestBody product: ProductInputDTO,
    ): ResponseEntity<*> {
        return ResponseEntity.ok(serviceProduct.addNewProduct(product).getOrThrow())
    }

    @PutMapping
    fun updateProduct(
        @RequestBody product: ProductUpdateInputDTO,
    ): ResponseEntity<ProductUpdateOutputDTO> {
        return ResponseEntity.ok(serviceProduct.updateProduct(product).getOrThrow())
    }

    @DeleteMapping
    fun deleteProduct(
        @RequestParam pid: Long,
    ): ResponseEntity<ProductDeleteOutputDTO> {
        return ResponseEntity.ok(serviceProduct.deleteProduct(pid).getOrThrow())
    }

    @PutMapping("{pid}/image")
    fun updateProductImage(
        @RequestParam("img") img: MultipartFile,
        @PathVariable pid: Long,
    ): ResponseEntity<ProductUpdateOutputDTO> {
        return ResponseEntity.ok(serviceProduct.updateProductImage(img, pid).getOrThrow())
    }

    @GetMapping("{pid}/image")
    fun getImageByIid(
        @PathVariable pid: Long,
        response: HttpServletResponse,
    ): ResponseEntity<ByteArray> {
        val result = serviceProduct.getImage(pid, response).getOrThrow()
        return ResponseEntity.ok().contentType(result.second).body(result.first)
    }

    @DeleteMapping("{pid}/image")
    fun deleteProductImage(
        @PathVariable pid: Long,
    ): ResponseEntity<ProductDeleteOutputDTO> {
        return ResponseEntity.ok(serviceProduct.deleteProductImage(pid).getOrThrow())
    }

    @GetMapping("/{pid}")
    fun getByPid(
        @PathVariable pid: Long,
    ): ResponseEntity<ProductOutputDTO> {
        return ResponseEntity.ok(serviceProduct.getProductByPid(pid).getOrThrow())
    }
}
