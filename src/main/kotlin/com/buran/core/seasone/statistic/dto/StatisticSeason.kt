package com.buran.core.seasone.statistic.dto

import com.buran.core.seasone.matches.enums.MatchAction

data class StatisticSeason(
    val win: Int,
    val lose: Int,
    val draw: Int,
    val stats: Map<MatchAction, ActionTeamEnemy>,
    val bestPlayer: StatisticPlayer?
)
