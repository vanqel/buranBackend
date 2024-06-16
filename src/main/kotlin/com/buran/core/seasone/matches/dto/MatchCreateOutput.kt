package com.buran.core.seasone.matches.dto

import java.time.LocalDateTime

data class MatchCreateOutput(
    val id: Long,
    val enemy: String,
    val dateStart: LocalDateTime,
    val team: List<Long>,
)
