package com.buran.core.seasone.statistic.models

import com.buran.core.seasone.matches.enums.MatchAction
import com.buran.core.seasone.matches.models.tables.MatchTable
import org.jetbrains.exposed.sql.Table

object EnemyStatsView: Table(name = "v_enemy_s_m")
{
    val matchId = reference("match_id", MatchTable)
    val action = enumeration("action", MatchAction::class)
    val count = long("count")
    val title = varchar("title", 255)
}
