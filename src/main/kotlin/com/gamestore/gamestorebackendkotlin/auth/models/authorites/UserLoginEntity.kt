package io.dtechs.core.auth.models.authorites

import io.dtechs.core.auth.ExtendedLongEntity
import io.dtechs.core.auth.models.authorites.table.UserLoginTable
import io.dtechs.core.auth.models.users.table.UserTable
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserLoginEntity(id: EntityID<Long>) : ExtendedLongEntity(id, UserTable) {
    companion object : LongEntityClass<UserLoginEntity>(UserLoginTable)
    var user by UserLoginTable.user
    var access by UserLoginTable.accesstoken
    var refresh by UserLoginTable.refreshtoken
}
