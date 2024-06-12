package com.buran.core.seasone.matches.dto

import java.time.LocalDateTime

data class MatchCreateInput (
    val seasonId: Long,
    val title: String,
    val enemy: String,
    val dateTimeStart: LocalDateTime,
    val team: List<Long>
)
