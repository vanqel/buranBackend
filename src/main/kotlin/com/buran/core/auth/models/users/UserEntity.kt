package com.buran.core.auth.models.users

import com.buran.core.auth.dto.users.UserOutput
import com.buran.core.auth.models.roles.RoleEntity
import com.buran.core.auth.models.users.table.UserTable
import com.buran.core.auth.models.users.table.UsersRolesTable
import com.buran.core.config.ExtendedLongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Long>) : ExtendedLongEntity(id, UserTable) {
    companion object : LongEntityClass<UserEntity>(UserTable)

    var username by UserTable.username
    var password by UserTable.password
    var phone by UserTable.phone
    var email by UserTable.email
    var roles by RoleEntity via UsersRolesTable
    var isBlocked by UserTable.isBlocked

}
