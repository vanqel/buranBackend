@file:Suppress("unused")

package com.gamestore.gamestorebackendkotlin.auth.models.roles

import com.gamestore.gamestorebackendkotlin.auth.models.roles.table.RoleTable
import com.gamestore.gamestorebackendkotlin.auth.models.users.UserEntity
import com.gamestore.gamestorebackendkotlin.auth.models.users.table.UsersRolesTable
import com.gamestore.gamestorebackendkotlin.config.ExtendedLongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RoleEntity(id: EntityID<Long>) : ExtendedLongEntity(id, RoleTable) {
    companion object : LongEntityClass<RoleEntity>(RoleTable)

    var roleName by RoleTable.roleEnum

    var description by RoleTable.description

    var users by UserEntity via UsersRolesTable
}
