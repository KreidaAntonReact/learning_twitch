package ru.twitch.common.exceptions

import graphql.ErrorType
import graphql.GraphQLError
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import ru.twitch.common.utils.ErrorMessage
import ru.twitch.common.utils.NotFoundException

@ControllerAdvice
class GlobalExceptionHandler {

    @GraphQlExceptionHandler
    fun handleGenericNotFound(ex: NotFoundException): GraphQLError {
        val error = ErrorMessage(
            errorType = ErrorType.DataFetchingException,
            message = ex.message
        )


        return GraphQLError.newError()
            .errorType(error.errorType)
            .message(error.message)
            .build()
    }
}