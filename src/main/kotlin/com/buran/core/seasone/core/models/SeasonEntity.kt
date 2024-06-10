package com.buran.core.seasone.core.models

import com.buran.core.config.ExtendedLongEntity
import com.buran.core.seasone.core.dto.SeasonOutput
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class SeasonEntity(id: EntityID<Long>): ExtendedLongEntity(id, SeasonTable){
    companion object : LongEntityClass<SeasonEntity>(SeasonTable)
    var title by SeasonTable.title
    var dateStart by SeasonTable.dateStart
    var dateEnd by SeasonTable.dateEnd

    fun toDTO() = SeasonOutput(
        id = this.id.value,
        dateStart = dateStart,
        dateEnd = dateEnd
    )
}
