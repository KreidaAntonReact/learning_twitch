package ru.twitch.users.services

import org.springframework.stereotype.Service
import ru.twitch.users.domians.UserEntity
import ru.twitch.users.dto.UserRequestCreateDto
import ru.twitch.users.reposotories.UserRepository
import ru.twitch.users.utils.UserNotFoundException
import java.util.UUID

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(user: UserRequestCreateDto): UserEntity = userRepository.createUser(user);

    fun getAllUsers(): List<UserEntity> = userRepository.findAllUser();

    fun findUserById(id: UUID): UserEntity? = userRepository.findUserById(id);
}