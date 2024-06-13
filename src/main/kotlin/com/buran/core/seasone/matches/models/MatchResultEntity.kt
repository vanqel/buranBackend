package com.buran.core.seasone.matches.models

import com.buran.core.config.ExtendedLongEntity
import com.buran.core.players.models.PlayerEntity
import com.buran.core.seasone.matches.dto.MatchResult
import com.buran.core.seasone.matches.models.tables.MatchResultTable
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class MatchResultEntity(id: EntityID<Long>): ExtendedLongEntity(id, MatchResultTable){

    companion object: LongEntityClass<MatchResultEntity>(MatchResultTable)
    val matchId by  MatchResultTable.matchId
    var playerId by MatchResultTable.playerId
    var minutes by MatchResultTable.minutes
    var action by MatchResultTable.action
    var enemy by MatchResultTable.enemy

    fun toDto() = MatchResult(
        playerId = playerId?.value,
        enemy = enemy,
        minutes = minutes,
        action = action
    )
}
