package io.dtechs.core.auth.models.users.table

import io.dtechs.core.auth.ExtendedLongIdTable

object UserTable : ExtendedLongIdTable(name = "users") {
    val username = varchar("username", length = 100)

    val password = varchar("password", length = 100)

    val phone = varchar("phone", length = 100)

    val email = varchar("email", length = 100)

    var snils = varchar("SNILS", length = 11)

    var refid = varchar("refid", length = 20)

    var isBlocked = bool("isBlocked").default(false)

    init {
        uniqueIndex(
            customIndexName = "USERS_USERNAME_UNIQUE",
            columns = arrayOf(username),
        )
    }
}
