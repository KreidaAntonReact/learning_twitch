package ru.twitch.users.reposotories

import ru.twitch.users.domians.UserEntity
import ru.twitch.users.dto.UserRequestCreateDto
import java.util.UUID

interface UserRepository {
    fun createUser(user: UserRequestCreateDto): UserEntity;
    fun findAllUser(): List<UserEntity>;
    fun findUserById(id: UUID): UserEntity?;
}