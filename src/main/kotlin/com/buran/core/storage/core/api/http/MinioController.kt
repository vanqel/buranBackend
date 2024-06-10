package com.buran.core.storage.core.api.http

import com.buran.core.storage.core.service.MinioService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("api/storage")
class MinioController(
    val service: MinioService,
) {

    @GetMapping()
    fun getObject(
        @RequestParam uuid: String,
    ):String {
        return "redirect:${service.getObject(uuid)}"
    }

    @PostMapping
    fun addObject(
        @RequestParam m: MultipartFile,
    ): String {
        return service.addObject(m)
    }
}

