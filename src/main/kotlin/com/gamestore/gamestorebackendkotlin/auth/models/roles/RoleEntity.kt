@file:Suppress("unused")

package io.dtechs.core.auth.models.roles

import io.dtechs.core.auth.ExtendedLongEntity
import io.dtechs.core.auth.models.roles.table.RoleTable
import io.dtechs.core.auth.models.users.UserEntity
import io.dtechs.core.auth.models.users.table.UsersRolesTable
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RoleEntity(id: EntityID<Long>) : ExtendedLongEntity(id, RoleTable) {
    companion object : LongEntityClass<RoleEntity>(RoleTable)

    var roleName by RoleTable.roleEnum

    var description by RoleTable.description

    var users by UserEntity via UsersRolesTable
}
