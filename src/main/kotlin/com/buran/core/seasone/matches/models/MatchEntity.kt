package com.buran.core.seasone.matches.models

import com.buran.core.config.ExtendedLongEntity
import com.buran.core.seasone.core.models.SeasonEntity
import com.buran.core.seasone.core.models.SeasonTable
import com.buran.core.seasone.matches.models.tables.MatchTable
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class MatchEntity(id: EntityID<Long>): ExtendedLongEntity(id, MatchTable){

    companion object: LongEntityClass<MatchEntity>(MatchTable)

    val season by  MatchTable.season
    var enemy by MatchTable.enemy
    var dateTimeStart by MatchTable.dateTimeStart
}

