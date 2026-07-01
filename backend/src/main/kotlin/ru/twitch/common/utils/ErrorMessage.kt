package ru.twitch.common.utils

import com.fasterxml.jackson.annotation.JsonInclude
import graphql.ErrorType
import java.util.Date

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorMessage (
    val errorType: ErrorType = ErrorType.ExecutionAborted,
    val message: String? = "Server Error",
    val description: String? = null,
    val timestamp: Date = Date()
)