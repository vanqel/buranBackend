package com.buran.core.seasone.matches.models.tables

import com.buran.core.config.ExtendedLongIdTable
import com.buran.core.players.models.PlayerTable
import org.jetbrains.exposed.sql.ColumnDiff
import org.jetbrains.exposed.sql.Index
import org.jetbrains.exposed.sql.ReferenceOption

object MatchTeams: ExtendedLongIdTable(name = "matches_teams") {
    val match = reference(
        "matches_id",
        MatchTable,
        onDelete = ReferenceOption.CASCADE
    )
    val team = reference(
        "player_id",
        PlayerTable,
        onDelete = ReferenceOption.CASCADE
    )
    val matchTeamIndex = Index(listOf( match, team), unique = false)

}
