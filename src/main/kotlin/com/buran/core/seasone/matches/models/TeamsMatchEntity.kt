package com.buran.core.seasone.matches.models

import com.buran.core.config.ExtendedLongEntity
import com.buran.core.players.models.PlayerEntity
import com.buran.core.seasone.matches.models.tables.MatchTeams
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TeamsMatchEntity(id: EntityID<Long>): ExtendedLongEntity(id, MatchTeams){
    companion object : LongEntityClass<TeamsMatchEntity>(MatchTeams)

    var matchId by MatchEntity via MatchTeams
    var teamId by PlayerEntity via MatchTeams
}
