package com.buran.core.auth.services.security.app

import com.buran.core.auth.errors.AuthError
import com.buran.core.auth.models.users.UserEntity
import com.buran.core.auth.models.users.table.UserTable
import org.jetbrains.exposed.dao.with
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AppUserDetailsService : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    @Transactional
    override fun loadUserByUsername(s: String): UserDetails {
        val userEntity: UserEntity =
            UserEntity
                .find { UserTable.username eq s }
                .with(UserEntity::roles)
                .singleOrNull()
                ?: throw AuthError()

        return User(
            userEntity.username,
            userEntity.password,
            userEntity.roles.map { SimpleGrantedAuthority(it.roleName.constantRole) },
        )
    }
}
