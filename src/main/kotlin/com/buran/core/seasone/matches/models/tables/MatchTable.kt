package com.buran.core.seasone.matches.models.tables

import com.buran.core.config.ExtendedLongEntity
import com.buran.core.config.ExtendedLongIdTable
import com.buran.core.players.models.PlayerEntity
import com.buran.core.players.models.PlayerTable
import com.buran.core.seasone.core.models.SeasonTable
import com.buran.core.seasone.matches.models.MatchEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object MatchTable: ExtendedLongIdTable(name = "matches") {
    val season = reference(
        "seasons_id",
        SeasonTable,
        onDelete = ReferenceOption.CASCADE
    )
    val enemy: Column<String> = varchar("enemy", 255)
    val dateTimeStart: Column<LocalDateTime> = datetime("dateTime")
}




