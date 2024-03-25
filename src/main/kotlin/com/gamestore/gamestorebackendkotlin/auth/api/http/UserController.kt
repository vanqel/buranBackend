package io.dtechs.core.auth.api.http

import com.github.michaelbull.result.getOrThrow
import io.dtechs.core.auth.dto.users.UserBlockOutput
import io.dtechs.core.auth.dto.users.UserChangePasswordInput
import io.dtechs.core.auth.dto.users.UserChangePasswordOutput
import io.dtechs.core.auth.dto.users.UserCreateInput
import io.dtechs.core.auth.dto.users.UserOutput
import io.dtechs.core.auth.dto.users.UserUpdateInput
import io.dtechs.core.auth.services.user.IUserService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: IUserService,
) {

    @PostMapping("register")
    fun register(
        @RequestBody body: UserCreateInput,
    ): ResponseEntity<UserOutput> {
        return ok(userService.registerUser(body).getOrThrow())
    }

    @PostMapping("updateUser")
    fun updateUser(
        @RequestBody body: UserUpdateInput,
    ): ResponseEntity<UserOutput> {
        return ok(userService.updateUser(body).getOrThrow())
    }

    @PostMapping("blockUser")
    fun blockUser(
        @RequestParam username: String? = null,
        @RequestParam userId: Long? = null,
    ): ResponseEntity<UserBlockOutput> {
        return ok(userService.blockUser(username, userId).getOrThrow())
    }

    @PostMapping("updatePassword")
    fun updatePassword(
        @RequestBody body: UserChangePasswordInput,
    ): ResponseEntity<UserChangePasswordOutput> {
        return ok(userService.updatePassword(body).getOrThrow())
    }
}
