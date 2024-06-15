package com.buran.core.seasone.statistic.models

import com.buran.core.players.models.PlayerTable
import com.buran.core.seasone.core.models.SeasonTable
import com.buran.core.seasone.matches.enums.MatchAction
import com.buran.core.seasone.matches.models.tables.MatchTable
import org.jetbrains.exposed.sql.Table

object MatchResultStatsView: Table(name = "v_match_global_result")
{

    val matchId = reference("match_id", MatchTable)
    val seasonId = reference("season", SeasonTable)
    val win = bool("win")
    val lose = bool("lose")
    val draw = bool("draw")
}
