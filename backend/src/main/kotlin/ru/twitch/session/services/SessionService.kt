package ru.twitch.session.services

import org.springframework.stereotype.Service
import ru.twitch.common.utils.ForbiddenException
import ru.twitch.common.utils.NotFoundException
import ru.twitch.session.dto.LoginInput
import ru.twitch.users.domian.UserEntity
import ru.twitch.users.reposotories.UserRepository

@Service
class SessionService (
    private val userRepository: UserRepository
) {
    fun login(dataLogin: LoginInput): UserEntity {
         val user = userRepository.findUserByEmail(dataLogin.login) ?: userRepository.findUserByUsername(dataLogin.login)

        if (user == null) {
            throw NotFoundException("User not found")
        }

        val isValidPassword = user.password == dataLogin.password

        if (!isValidPassword) {
            throw ForbiddenException("Passwords do not match")
        }

        return user;
    }
}