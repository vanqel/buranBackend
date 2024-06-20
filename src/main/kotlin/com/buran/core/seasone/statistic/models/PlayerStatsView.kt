package com.buran.core.seasone.statistic.models

import com.buran.core.players.models.PlayerTable
import com.buran.core.seasone.matches.enums.MatchAction
import com.buran.core.seasone.matches.models.tables.MatchTable
import org.jetbrains.exposed.sql.Table

object PlayerStatsView: Table(name = "v_player_s_m")
{
    val playerId = reference("player_id", PlayerTable).nullable()
    val matchId = reference("match_id", MatchTable)
    val action = enumeration("action", MatchAction::class)
    val title = varchar("title", 255)
}
