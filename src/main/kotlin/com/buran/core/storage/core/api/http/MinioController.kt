package com.buran.core.storage.core.api.http

import com.buran.core.storage.core.service.FileOutput
import com.buran.core.storage.core.service.MinioService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("api/storage")
class MinioController(
    val service: MinioService,
) {

    @GetMapping()
    fun getObject(
        @RequestParam uuid: String,
    ): FileOutput? {
        return service.getObject(UUID.fromString(uuid))
    }

    @PostMapping
    fun addObject(
        @RequestParam file: MultipartFile,
    ): FileOutput {
        return service.addObject(file)
    }

    @DeleteMapping
    fun deleteObject(
        @RequestParam uuid: String,
    ): Boolean {
        return service.delObject(uuid)
    }
}

