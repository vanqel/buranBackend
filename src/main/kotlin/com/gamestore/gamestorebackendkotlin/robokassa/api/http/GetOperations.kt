package com.gamestore.gamestorebackendkotlin.robokassa.api.http

import com.gamestore.gamestorebackendkotlin.robokassa.dto.OperationsOutput
import com.gamestore.gamestorebackendkotlin.robokassa.service.OperationService
import com.github.michaelbull.result.getOrThrow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/useroperations")
class GetOperations(val service: OperationService) {
    @GetMapping("my")
    fun getOperations(): ResponseEntity<List<OperationsOutput>> {
        return ResponseEntity.ok(service.getOperations().getOrThrow())
    }
}
