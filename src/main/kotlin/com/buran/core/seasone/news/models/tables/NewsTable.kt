package com.buran.core.seasone.news.models.tables

import com.buran.core.config.ExtendedLongIdTable
import com.buran.core.seasone.core.models.SeasonTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate
import java.util.UUID

object NewsTable : ExtendedLongIdTable(name = "news_seasons") {
    val parentKey : Column<UUID> = uuid("parentKey").autoGenerate()
    val title: Column<String> = varchar("title", 255)
    val text: Column<String> = varchar("text", 255)
    val date: Column<LocalDate> = date("date")
    val season = reference(
        "seasons_id",
        SeasonTable,
        onDelete = ReferenceOption.CASCADE
    )
}
