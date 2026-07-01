package ru.twitch.users.controllers

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import ru.twitch.users.domians.UserEntity
import ru.twitch.users.services.UserService
import java.util.UUID

@Controller
class UserController(private val userService: UserService) {
    @QueryMapping
    fun users(): List<UserEntity> = userService.getAllUsers()

    @QueryMapping
    fun userById(@Argument id: UUID): UserEntity? = userService.findUserById(id)
}