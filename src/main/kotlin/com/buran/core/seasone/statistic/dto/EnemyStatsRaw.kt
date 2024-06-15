package com.buran.core.seasone.statistic.dto

import com.buran.core.seasone.matches.enums.MatchAction

data class EnemyStatsRaw(
    val matchId: Long,
    val action: MatchAction,
    val count: Long,
    val title: String
)
