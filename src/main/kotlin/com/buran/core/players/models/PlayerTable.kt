package com.buran.core.players.models

import com.buran.core.config.ExtendedLongIdTable
import com.buran.core.players.enums.PlayerType
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate
import java.util.*

object PlayerTable: ExtendedLongIdTable(name = "players") {
    val name: Column<String> = varchar("name", 255)
    val biography: Column<String> = text("biography")
    val number: Column<Int> = integer("number")
    val photo: Column<UUID> = uuid("photo")
    val birthDate: Column<LocalDate> = date("birth_date")
    val type: Column<PlayerType> = enumeration("type", PlayerType::class)
    val archived: Column<Boolean> = bool("archived").default(false)
}



