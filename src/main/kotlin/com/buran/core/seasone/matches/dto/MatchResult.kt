package com.buran.core.seasone.matches.dto

import com.buran.core.seasone.matches.enums.MatchAction

data class MatchResult(
    val matchId: Long,
    val playerId: Long?,
    val enemy: Boolean,
    val minutes: Long,
    val action: MatchAction,
)
