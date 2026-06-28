package ru.twitch.users.domians


import java.time.LocalDateTime
import java.util.UUID

data class UserEntity(
    val id: UUID? = null,
    val firstName: String,
    val lastName: String,

    val email: String,

    val createAt: LocalDateTime,
    val updateAt: LocalDateTime
)
