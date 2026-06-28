package ru.twitch.users.dto


data class UserRequestCreateDto (
    val email: String,
    val firstname: String,
    val lastname: String,
)
