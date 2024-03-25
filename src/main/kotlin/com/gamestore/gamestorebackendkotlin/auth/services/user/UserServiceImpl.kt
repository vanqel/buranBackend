package com.gamestore.gamestorebackendkotlin.auth.services.user

import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserBlockOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserChangePasswordInput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserChangePasswordOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserCreateInput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserOutput
import com.gamestore.gamestorebackendkotlin.auth.dto.users.UserUpdateInput
import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.auth.repository.UsersRepository
import com.gamestore.gamestorebackendkotlin.auth.validation.ChainValidate
import com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.db.ValidateEmailDB
import com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.db.ValidateLoginDB
import com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.db.ValidateUserIdDB
import com.gamestore.gamestorebackendkotlin.auth.validation.validatorsChain.users.field.ValidatePasswordField
import com.gamestore.gamestorebackendkotlin.extensions.Result
import com.gamestore.gamestorebackendkotlin.extensions.error
import com.gamestore.gamestorebackendkotlin.extensions.isNotNull
import com.gamestore.gamestorebackendkotlin.extensions.isNull
import com.gamestore.gamestorebackendkotlin.extensions.ok
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    val usersRepository: UsersRepository,
    val chainValidate: ChainValidate<String>,
    val validateUserIdDB: ValidateUserIdDB,
    val validateLoginDB: ValidateLoginDB,
    val validateEmailDB: ValidateEmailDB,
) : IUserService {
    override fun registerUser(body: UserCreateInput): Result<UserOutput> {
        body.validate()?.let { return it }

        chainValidate.builder()
            .addChain(validateLoginDB, body.username)
            .addChain(validateEmailDB, body.email)
            .findFirstException()?.let { return it }

        val user: UserOutput = usersRepository.save(body)

        return Result.ok(user)
    }

    override fun updateUser(body: UserUpdateInput): Result<UserOutput> {
        body.validate()?.let { return it }

        chainValidate.builder()
            .addChain(validateUserIdDB, body.id.toString())
            .addChain(validateEmailDB, body.newEmail)
            .findFirstException()?.let { return it }

        val user: UserOutput = usersRepository.updateUser(body)

        return Result.ok(user)
    }

    override fun blockUser(
        username: String?,
        userId: Long?,
    ): Result<UserBlockOutput> {
        if (username.isNull() && userId.isNull()) {
            return Result.error(ValidationError("Такого пользователя не существует"))
        }

        val result: UserBlockOutput? =
            if (username.isNotNull() &&
                userId.isNotNull() &&
                (usersRepository.compareIdAndUsername(username!!, userId!!)) // Переделать
            ) {
                usersRepository.blockUser(userId)
            } else {
                if (username.isNull()) {
                    usersRepository.findUserById(userId!!)?.let {
                        usersRepository.blockUser(it.id.value)
                    }
                } else {
                    usersRepository.findUserByUsername(username!!)?.let {
                        usersRepository.blockUser(it.id.value)
                    }
                }
            }
        return result?.let {
            Result.ok(it)
        } ?: Result.error(ValidationError("Такого пользователя не существует"))
    }

    override fun updatePassword(body: UserChangePasswordInput): Result<UserChangePasswordOutput> {
        chainValidate.builder()
            .addChain(ValidatePasswordField(), body.newPassword)
            .findAllException()?.let {
                return it
            }
        return ValidateLoginDB(usersRepository).valid(body.username)?.let {
            Result.ok(usersRepository.updatePassword(body))
        } ?: Result.error(ValidationError("Такого пользователя не существует"))
    }
}
