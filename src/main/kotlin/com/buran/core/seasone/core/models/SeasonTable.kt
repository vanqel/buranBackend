package com.buran.core.seasone.core.models

import com.buran.core.config.ExtendedLongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object SeasonTable : ExtendedLongIdTable(name = "seasons") {
    val title: Column<String> = varchar("title", 255).uniqueIndex()
    val dateStart: Column<LocalDate> = date("date_start")
    val dateEnd: Column<LocalDate> = date("date_end")
}
