package com.zeronsoftn.honginsung.userservice.adapter.input.graphql

import com.zeronsoftn.honginsung.userservice.domain.exception.DuplicateEmailException
import com.zeronsoftn.honginsung.userservice.domain.exception.InvalidEmailException
import com.zeronsoftn.honginsung.userservice.domain.exception.InvalidUserNameException
import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler
import org.springframework.graphql.execution.ErrorType
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class UserGraphQlExceptionHandler {

    @GraphQlExceptionHandler
    fun handleDuplicateEmailException(
        errorBuilder: GraphqlErrorBuilder<*>,
        exception: DuplicateEmailException
    ): GraphQLError {
        return errorBuilder
            .errorType(ErrorType.BAD_REQUEST)
            .message(
                exception.message ?: "이미 등록된 이메일입니다.",
            )
            .extensions(
                mapOf<String, Any>(
                    "code" to "DUPLICATE_EMAIL"
                )
            )
            .build()
    }

    @GraphQlExceptionHandler
    fun handleInvalidEmailException(
        errorBuilder: GraphqlErrorBuilder<*>,
        exception: InvalidEmailException
    ) : GraphQLError {
        return errorBuilder
            .errorType(ErrorType.BAD_REQUEST)
            .message(
                exception.message ?: "올바른 이메일 형식이 아닙니다."
            )
            .extensions(
                mapOf<String, Any>(
                    "code" to "INVALID_EMAIL"
                )
            )
            .build()
    }

    @GraphQlExceptionHandler
    fun handleInvalidUserNameException(
        errorBuilder: GraphqlErrorBuilder<*>,
        exception: InvalidUserNameException
    ) : GraphQLError {
        return errorBuilder
            .errorType(ErrorType.BAD_REQUEST)
            .message(
                exception.message ?: "사용자 이름은 비어 있을 수 없습니다."
            )
            .extensions(
                mapOf<String, Any>(
                    "code" to "INVALID_USERNAME"
                )
            )
            .build()
    }
}