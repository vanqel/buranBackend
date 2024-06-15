package com.buran.core.seasone.statistic.dto

import com.buran.core.seasone.matches.enums.MatchAction

data class PlayerStatsRaw(
    val playerId: Long,
    val matchId: Long,
    val action: MatchAction,
    val count: Long,
    val title: String
)
