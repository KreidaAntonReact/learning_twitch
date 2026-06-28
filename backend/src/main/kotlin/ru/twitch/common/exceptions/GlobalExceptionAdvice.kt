package ru.twitch.common.exceptions

import ru.twitch.common.utils.ErrorMessage
import ru.twitch.users.utils.UserNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Date


@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityException(
        ex: DataIntegrityViolationException
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            status = HttpStatus.CONFLICT,
            message = "Database constraint violation",
            timestamp = Date()
        )

        return ResponseEntity(errorMessage, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(
        ex: UserNotFoundException
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            status = HttpStatus.NOT_FOUND,
            message = "User id ${ex.id} not found",
            timestamp = Date()
        )

        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
}