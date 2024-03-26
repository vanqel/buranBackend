package com.gamestore.gamestorebackendkotlin.auth.models.users

import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserOutput
import com.gamestore.gamestorebackendkotlin.auth.models.roles.RoleEntity
import com.gamestore.gamestorebackendkotlin.auth.models.users.table.UserTable
import com.gamestore.gamestorebackendkotlin.auth.models.users.table.UsersRolesTable
import com.gamestore.gamestorebackendkotlin.config.ExtendedLongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

//
// @Entity
// @Table(name = "users")
// class User(
//    @Id
//    @GeneratedValue
//    val id: Long?,
//
//    @Column(name = "username", unique = true)
//    var username: String,
//
//    @JsonIgnore
//    @Column(name = "password")
//    var password: String? = null,
//
//    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
//    @JoinTable(
//        name = "user_role",
//        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
//        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
//    )
//    var roles: Set<io.dtechs.core.auth.roles.Role>,
//
//    @Column(name = "employee_id")
//    var employeeId: Long,
//
//    @Column(name = "phone", length = 20)
//    var phone: String? = null,
//
//    @Column(name = "email", length = 50)
//    private var email: String? = null,
//
//    /**
//     * Пользователь доступен/недоступен
//     */
//    @Column(name = "enabled")
//    @NotNull
//    private var enabled: Boolean = false,
//
//    /**
//     * Ссылка на объект запись системы, к которому привязывается пользователь
//     */
//    @Column(name = "REF_ID")
//    private val referenceId: Long,
//
//    @Column(name = "deleted_at")
//    var deletedAt: OffsetDateTime? = null
// ) {
// //    fun toDto() = UserResponse(
// //
// //    )
//
//    /*
//    * TODO 05.02.2024 sla: updateUser
//    */
//
//
//    fun block() {
//        if (this.deletedAt != null) throw GeneralException("Пользователь уже заблокирован")
// //        if (this.hasAdminRole()) {
// //            throw GeneralException("Невозможно удалить данного пользователя")
// //        }
//        this.deletedAt = OffsetDateTime.now()
//    }
//
//    fun isActive(): Boolean {
//        return this.deletedAt == null
//    }
// }

class UserEntity(id: EntityID<Long>) : ExtendedLongEntity(id, UserTable) {
    companion object : LongEntityClass<UserEntity>(UserTable)

    var username by UserTable.username
    var password by UserTable.password
    var phone by UserTable.phone
    var email by UserTable.email
    var roles by RoleEntity via UsersRolesTable
    var isBlocked by UserTable.isBlocked

    fun toDTO(): UserOutput {
        return UserOutput(this)
    }
}
