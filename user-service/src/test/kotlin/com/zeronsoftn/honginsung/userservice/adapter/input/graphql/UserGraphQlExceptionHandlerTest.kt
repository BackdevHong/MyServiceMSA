package com.zeronsoftn.honginsung.userservice.adapter.input.graphql

import com.zeronsoftn.honginsung.userservice.domain.exception.DuplicateEmailException
import com.zeronsoftn.honginsung.userservice.domain.exception.InvalidEmailException
import com.zeronsoftn.honginsung.userservice.domain.exception.InvalidUserNameException
import graphql.GraphqlErrorBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.graphql.execution.ErrorType

class UserGraphQlExceptionHandlerTest {

    @Test
    fun duplicateEmailExceptionTest() {
        // given
        val handler = UserGraphQlExceptionHandler()

        val exception = DuplicateEmailException()

        // when
        val error = handler.handleDuplicateEmailException(
            errorBuilder = GraphqlErrorBuilder.newError(),
            exception = exception,
        )

        // then
        assertEquals(
            "이미 등록된 이메일입니다.",
            error.message,
        )

        assertEquals(
            ErrorType.BAD_REQUEST,
            error.errorType,
        )

        assertEquals(
            "DUPLICATE_EMAIL",
            error.extensions?.get("code"),
        )
    }

    @Test
    fun invalidEmailExceptionTest() {
        val handler = UserGraphQlExceptionHandler()

        val exception = InvalidEmailException()

        val error = handler.handleInvalidEmailException(
            errorBuilder = GraphqlErrorBuilder.newError(),
            exception = exception,
        )

        assertEquals(
            "올바른 이메일 형식이 아닙니다.",
            error.message,
        )

        assertEquals(
            ErrorType.BAD_REQUEST,
            error.errorType,
        )

        assertEquals(
            "INVALID_EMAIL",
            error.extensions?.get("code"),
        )
    }

    @Test
    fun invalidUserNameExceptionTest() {
        val handler = UserGraphQlExceptionHandler()

        val exception = InvalidUserNameException()

        val error = handler.handleInvalidUserNameException(
            errorBuilder = GraphqlErrorBuilder.newError(),
            exception = exception,
        )

        assertEquals(
            "사용자 이름은 비어 있을 수 없습니다.",
            error.message,
        )

        assertEquals(
            ErrorType.BAD_REQUEST,
            error.errorType,
        )

        assertEquals(
            "INVALID_USERNAME",
            error.extensions?.get("code"),
        )
    }
}