package com.buran.core.seasone.news.models

import com.buran.core.config.ExtendedLongEntity
import com.buran.core.seasone.core.models.SeasonEntity
import com.buran.core.seasone.news.models.tables.NewsTable
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class NewsEntity(id: EntityID<Long>) : ExtendedLongEntity(id, NewsTable) {

    companion object : LongEntityClass<NewsEntity>(NewsTable)

    val parentKey by NewsTable.parentKey
    var title by NewsTable.title
    var text by NewsTable.text
    var date by NewsTable.date
    var season by SeasonEntity via NewsTable
}
