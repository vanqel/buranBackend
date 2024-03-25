package io.dtechs.core.auth.dto.users

import io.dtechs.core.auth.models.roles.table.RolesEnum
import io.dtechs.core.auth.models.users.UserEntity

class UserOutput(
    val id: Long,
    val username: String,
    val roles: List<RolesEnum>,
    val phone: String,
    val email: String,
    val snils: String,
    val refid: String,
    val isBlocked: Boolean,
) {
    constructor(u: UserEntity) : this(
        u.id.value,
        u.username,
        u.roles.map { it.roleName },
        u.phone,
        u.email,
        u.snils,
        u.refid,
        u.isBlocked,
    )
}
