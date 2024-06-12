package com.buran.core.players.dto

import com.buran.core.players.enums.PlayerType
import java.time.LocalDate
import java.util.UUID

data class PlayerCreateInput(
    val name: String,
    val number: Int,
    val biography: String,
    val photo: UUID,
    val birthDate: LocalDate,
    val type: PlayerType
)


