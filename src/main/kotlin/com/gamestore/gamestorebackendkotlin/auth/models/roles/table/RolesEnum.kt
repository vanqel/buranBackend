package io.dtechs.core.auth.models.roles.table

object ConstantRoles {
    const val ADMIN = "ADMIN"
    const val DOCTOR = "DOCTOR"
    const val LOGIN = "LOGIN"
}

enum class RolesEnum(
    val id: Long,
    val constantRole: String,
) {
    ADMIN(id = 1, constantRole = ConstantRoles.ADMIN),
    DOCTOR(id = 2, constantRole = ConstantRoles.DOCTOR),
    LOGIN(id = 3, constantRole = ConstantRoles.LOGIN),
}
