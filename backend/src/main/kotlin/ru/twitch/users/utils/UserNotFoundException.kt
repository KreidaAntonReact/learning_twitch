package ru.twitch.users.utils

import java.util.UUID


class UserNotFoundException (val id: UUID): RuntimeException("User not found with id: $id")