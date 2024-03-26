package com.gamestore.gamestorebackendkotlin.robokassa.api.http

import com.gamestore.gamestorebackendkotlin.robokassa.service.RoboKassaService
import com.github.michaelbull.result.getOrThrow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kassa")
class RoboKassaContoller(val service: RoboKassaService) {
    @GetMapping("{pid}")
    fun getLinkFromProduct(
        @PathVariable pid: Long,
    ): ResponseEntity<String> {
        return ResponseEntity.ok(service.generatePaymentLink(pid).getOrThrow())
    }
}
