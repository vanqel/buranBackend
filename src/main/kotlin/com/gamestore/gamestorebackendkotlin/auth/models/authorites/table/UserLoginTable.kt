package com.gamestore.gamestorebackendkotlin.auth.models.authorites.table

import com.gamestore.gamestorebackendkotlin.auth.models.users.table.UserTable
import com.gamestore.gamestorebackendkotlin.config.ExtendedLongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption

object UserLoginTable : ExtendedLongIdTable(name = "user_auth") {
    val user =
        reference(
            "user_id",
            UserTable,
            onDelete = ReferenceOption.CASCADE,
        )

    val accesstoken: Column<String> = varchar(name = "accessToken", length = 500)

    val refreshtoken: Column<String> = varchar(name = "refreshToken", length = 300)
}
