package ru.twitch.users.domians


import java.time.LocalDateTime
import java.util.UUID

data class UserEntity(
    val id: UUID? = null,
    val username: String,
    val password: String,
    val email: String,
    val displayName: String,

    val bio: String? = null,
    val avatar: String? = null,

    val createAt: LocalDateTime,
    val updateAt: LocalDateTime
)
