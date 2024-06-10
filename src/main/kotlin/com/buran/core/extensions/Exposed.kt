package com.buran.core.extensions

import com.buran.core.config.ExtendedLongIdTable
import org.jetbrains.exposed.sql.*

fun SizedIterable<*>.exists() = this.empty().not()

fun ExtendedLongIdTable.selectAllNotDeleted(): Query =
    Query(this, null)
        .andWhere { this@selectAllNotDeleted.deletedAt eq null }
