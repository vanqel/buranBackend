package com.gamestore.gamestorebackendkotlin.robokassa.dto

data class PaymentInput(
    val OutSum: Int,
    val InvId: Int,
    val SignatureValue:String
)
