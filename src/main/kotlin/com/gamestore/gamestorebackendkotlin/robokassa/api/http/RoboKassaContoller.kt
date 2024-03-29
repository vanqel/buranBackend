package com.gamestore.gamestorebackendkotlin.robokassa.api.http

import com.gamestore.gamestorebackendkotlin.robokassa.service.RoboKassaService
import com.github.michaelbull.result.getOrThrow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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

    @PostMapping("success")
    fun verifyPayment(@RequestParam OutSum: String,
                      @RequestParam InvId: Int,
                      @RequestParam SignatureValue: String){
         service.succ(OutSum, InvId, SignatureValue).getOrThrow()
    }
    @PostMapping("fail")
    fun failPayment(@RequestParam OutSum: String,
                      @RequestParam InvId: Int,
                      @RequestParam SignatureValue: String){
        service.fail(OutSum, InvId, SignatureValue).getOrThrow()
    }
}
