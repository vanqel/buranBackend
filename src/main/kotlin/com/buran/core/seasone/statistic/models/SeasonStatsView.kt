package com.buran.core.seasone.statistic.models

import com.buran.core.players.models.PlayerTable
import com.buran.core.seasone.core.models.SeasonTable
import com.buran.core.seasone.matches.enums.MatchAction
import com.buran.core.seasone.matches.models.tables.MatchTable
import org.jetbrains.exposed.sql.Table

object SeasonStatsView: Table(name = "v_season_statistic")
{
    val title = varchar("title", 20)
    val season_id = reference("id", SeasonTable)
    val action = enumeration("action", MatchAction::class)
    val team = long("team")
    val enemy = long("enemy")
}
