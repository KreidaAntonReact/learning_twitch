package ru.twitch.users.reposotories

import ru.twitch.users.domians.UserEntity
import ru.twitch.users.dto.UserRequestCreateDto
import ru.twitch.users.dto.UserUpdateDto
import java.util.UUID

interface UserRepository {
    fun createUser(user: UserRequestCreateDto): UserEntity;
    fun findAllUser(): List<UserEntity>;
    fun findUserById(id: UUID): UserEntity?;
    fun updateUserById(id: UUID, user: UserUpdateDto): UserEntity;
}