package com.buran.core.seasone.statistic.dto

import com.buran.core.players.dto.PlayerSimpleOutput
import com.buran.core.seasone.matches.enums.MatchAction

data class StatisticPlayer(
    val player: PlayerSimpleOutput,
    val actions: Map<MatchAction, Long>
)

