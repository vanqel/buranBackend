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
    var title by MatchTable.title
    var enemy by MatchTable.enemy
    var dateTimeStart by MatchTable.dateTimeStart
}
//
//package com.buran.core.seasone.core.models
//
//import com.buran.core.config.ExtendedLongEntity
//import com.buran.core.seasone.core.dto.SeasonOutput
//import org.jetbrains.exposed.dao.LongEntityClass
//import org.jetbrains.exposed.dao.id.EntityID
//
//class SeasonEntity(id: EntityID<Long>): ExtendedLongEntity(id, SeasonTable){
//    companion object : LongEntityClass<SeasonEntity>(SeasonTable)
//    var title by SeasonTable.title
//    var dateStart by SeasonTable.dateStart
//    var dateEnd by SeasonTable.dateEnd
//
//    fun toDTO() = SeasonOutput(
//        dateStart = dateStart,
//        dateEnd = dateEnd,
//        title = title
//    )
//}

