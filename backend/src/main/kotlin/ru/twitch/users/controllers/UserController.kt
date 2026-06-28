package ru.twitch.users.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.twitch.users.domians.UserEntity
import ru.twitch.users.dto.UserRequestCreateDto
import ru.twitch.users.dto.UserUpdateDto
import ru.twitch.users.services.UserService
import java.util.UUID

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
    @PostMapping
    fun createUser(@RequestBody user: UserRequestCreateDto): UserEntity = userService.createUser(user)

    @GetMapping
    fun getAllUsers(): List<UserEntity> = userService.getAllUsers()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): UserEntity? = userService.findUserById(id)

    @PatchMapping("/{id}")
    fun patchUserById(@PathVariable id: UUID, @RequestBody user: UserUpdateDto): UserEntity {
        return userService.updateUserById(id, user)
    }
}