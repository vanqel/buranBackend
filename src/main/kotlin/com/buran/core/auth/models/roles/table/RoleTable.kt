package com.buran.core.auth.models.roles.table

import com.buran.core.config.ExtendedLongIdTable
import org.jetbrains.exposed.sql.Column

object RoleTable : ExtendedLongIdTable(name = "roles") {
    val roleEnum: Column<RolesEnum> = enumerationByName(name = "role_name", length = 100) // readonly

    val description: Column<String?> = varchar("description", length = 1000).nullable() // readonly
}
