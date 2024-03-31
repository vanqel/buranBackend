package com.gamestore.gamestorebackendkotlin.robokassa.dto

data class PaymentInput(
    val OutSum: Double,
    val InvId: Int,
    val SignatureValue:String
)
