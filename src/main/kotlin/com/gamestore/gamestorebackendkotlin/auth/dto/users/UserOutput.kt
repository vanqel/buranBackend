package com.gamestore.gamestorebackendkotlin.auth.dto.users

import com.gamestore.gamestorebackendkotlin.auth.models.roles.table.RolesEnum
import com.gamestore.gamestorebackendkotlin.auth.models.users.UserEntity

class UserOutput(
    val id: Long,
    val username: String,
    val roles: List<RolesEnum>,
    val phone: String,
    val email: String,
    val isBlocked: Boolean,
) {
    constructor(u: UserEntity) : this(
        u.id.value,
        u.username,
        u.roles.map { it.roleName },
        u.phone,
        u.email,
        u.isBlocked,
    )
}
