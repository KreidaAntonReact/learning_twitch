package ru.twitch.users.services

import org.springframework.stereotype.Service
import ru.twitch.common.utils.CreateException
import ru.twitch.users.domian.UserEntity
import ru.twitch.users.dto.UserRequestCreateDto
import ru.twitch.users.reposotories.UserRepository
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun createUser(user: UserRequestCreateDto): UserEntity {
        val isExistUserEmail: Boolean =  userRepository.findUserByEmail(
            user.email
        ) != null

        if (isExistUserEmail) {
            throw CreateException("User email already exists")
        }

        val isExistUserUsername: Boolean = userRepository.findUserByUsername(user.username) != null

        if(isExistUserUsername) {
            throw CreateException("User username already exists")
        }

        return userRepository.createUser(user)
    };

    fun getAllUsers(): List<UserEntity> = userRepository.findAllUser();

    fun findUserById(id: UUID): UserEntity? = userRepository.findUserById(id);
}