package com.gamestore.gamestorebackendkotlin.auth.models.users.table

import com.gamestore.gamestorebackendkotlin.config.ExtendedLongIdTable

object UserTable : ExtendedLongIdTable(name = "users") {
    val username = varchar("username", length = 100)

    val password = varchar("password", length = 100)

    val phone = varchar("phone", length = 100)

    val email = varchar("email", length = 100)

    var isBlocked = bool("isBlocked").default(false)

    init {
        uniqueIndex(
            customIndexName = "USERS_USERNAME_UNIQUE",
            columns = arrayOf(username),
        )
    }
}
