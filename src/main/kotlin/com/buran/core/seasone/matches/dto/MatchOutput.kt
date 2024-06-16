package com.buran.core.seasone.matches.dto

import com.buran.core.players.dto.PlayerSimpleOutput
import java.time.LocalDateTime

data class MatchOutput(
    val id: Long,
    val enemy: String,
    val dateStart: LocalDateTime,
    val team: List<PlayerSimpleOutput>,
    val actions: List<MatchResult?>
)
