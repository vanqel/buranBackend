package com.gamestore.gamestorebackendkotlin.auth.models.authorites

import com.gamestore.gamestorebackendkotlin.auth.models.authorites.table.UserLoginTable
import com.gamestore.gamestorebackendkotlin.config.ExtendedLongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserLoginEntity(id: EntityID<Long>) : ExtendedLongEntity(id, UserLoginTable) {
    companion object : LongEntityClass<UserLoginEntity>(UserLoginTable)

    var user by UserLoginTable.user
    var access by UserLoginTable.accesstoken
    var refresh by UserLoginTable.refreshtoken
}
