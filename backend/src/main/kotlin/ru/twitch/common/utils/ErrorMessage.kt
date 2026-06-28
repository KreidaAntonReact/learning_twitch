package ru.twitch.common.utils

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import java.util.Date

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorMessage (
    val status: HttpStatus = HttpStatus.CONFLICT,
    val message: String? = "Server Error",
    val description: String? = null,
    val timestamp: Date = Date()
)