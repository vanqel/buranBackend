package com.gamestore.gamestorebackendkotlin.extensions

import com.gamestore.gamestorebackendkotlin.config.ExtendedLongIdTable
import org.jetbrains.exposed.sql.*

fun SizedIterable<*>.exists() = this.empty().not()

fun ExtendedLongIdTable.selectAllNotDeleted(): Query =
    Query(this, null)
        .andWhere { this@selectAllNotDeleted.deletedAt eq null }
