package com.buran.core.seasone.statistic.dto

data class SeasonStatsRaw(
    val id: Long,
    val title: String,
    val stats: ActionTeamEnemy
)

data class ActionTeamEnemy(
    val team: Long,
    val enemy: Long
)
