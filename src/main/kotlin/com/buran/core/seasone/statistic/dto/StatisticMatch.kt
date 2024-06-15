package com.buran.core.seasone.statistic.dto

import com.buran.core.seasone.matches.enums.MatchAction

data class StatisticMatch(
    val bestPlayer: StatisticPlayer?,
    val stats: Map<MatchAction, Long>
)
