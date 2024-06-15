package com.buran.core.seasone.statistic.dto

import com.buran.core.players.dto.PlayerSimpleOutput
import com.buran.core.seasone.matches.enums.MatchAction

data class StatisticPlayerScore(
    val player: PlayerSimpleOutput,
    val actions: Map<MatchAction, Long>,
    val score : Long
){
    fun toStatisticPlayer(): StatisticPlayer{
        return StatisticPlayer(player, actions)
    }
}
