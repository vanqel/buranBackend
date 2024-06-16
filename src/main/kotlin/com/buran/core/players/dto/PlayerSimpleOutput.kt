package com.buran.core.players.dto

import com.buran.core.players.enums.PlayerType

data class PlayerSimpleOutput (
    val id: Long,
    val name: String,
    val number: Int,
    val type: PlayerType,
    val url : String,
    val img: String
)
