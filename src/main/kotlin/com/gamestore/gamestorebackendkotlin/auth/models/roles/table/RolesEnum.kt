package com.gamestore.gamestorebackendkotlin.auth.models.roles.table

object ConstantRoles {
    const val ADMIN = "ADMIN"
    const val USER = "USER"
}

enum class RolesEnum(
    val id: Long,
    val constantRole: String,
) {
    ADMIN(id = 1, constantRole = ConstantRoles.ADMIN),
    USER(id = 1, constantRole = ConstantRoles.USER),
}
