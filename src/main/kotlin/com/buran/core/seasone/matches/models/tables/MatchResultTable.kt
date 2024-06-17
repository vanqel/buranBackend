package com.buran.core.seasone.matches.models.tables

import com.buran.core.config.ExtendedLongIdTable
import com.buran.core.players.models.PlayerTable
import com.buran.core.seasone.matches.enums.MatchAction
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Index
import org.jetbrains.exposed.sql.ReferenceOption

object MatchResultTable : ExtendedLongIdTable(name = "match_results") {
    val matchId = reference(
        "matches_id",
        MatchTable,
        onDelete = ReferenceOption.CASCADE
    )
    val playerId = reference(
        "player_id",
        PlayerTable,
        onDelete = ReferenceOption.CASCADE
    ).nullable()

    val enemy: Column<Boolean> = bool("enemy").default(false)
    val minutes: Column<Long> = long("minutes")
    val action: Column<MatchAction> = enumeration("action", MatchAction::class)

    val matchTeamIndex = Index(listOf( matchId, playerId), unique = false)

}
