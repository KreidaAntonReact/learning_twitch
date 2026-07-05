package ru.twitch.users.dto


data class UserRequestCreateDto (
    val username: String,
    val password: String,
    val displayName: String,
    val email: String,
    val bio: String?,
    val avatar: String?,
)
