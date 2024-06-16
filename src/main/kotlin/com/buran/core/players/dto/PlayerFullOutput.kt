package com.buran.core.players.dto

import com.buran.core.players.enums.PlayerType

data class PlayerFullOutput(
    val id: Long,
    val name: String,
    val number: Int,
    val biography: String,
    val birthDate: String,
    val type: PlayerType,
    val url: String,
    val img: String
)
