package com.zeronsoftn.honginsung.userservice.adapter.input.graphql

import com.zeronsoftn.honginsung.userservice.application.port.input.CreateUserCommand
import com.zeronsoftn.honginsung.userservice.application.port.input.CreateUserUseCase
import com.zeronsoftn.honginsung.userservice.domain.exception.DuplicateEmailException
import com.zeronsoftn.honginsung.userservice.domain.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class UserMutationControllerTest {
    @Test
    fun createUserMutationTest() {
        // given
        val userId = UUID.fromString(
            "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
        )

        val createUserUseCase =
            RecordingCreateUserUseCase(
                result = User.restore(
                    id = userId,
                    name = "홍길동",
                    email = "hong@example.com",
                ),
            )

        val controller = UserMutationController(
            createUserUseCase = createUserUseCase,
        )

        val input = CreateUserInput(
            name = "홍길동",
            email = "hong@example.com",
        )

        // when
        val result = controller.createUser(input)

        // then
        assertEquals(
            CreateUserCommand(
                name = "홍길동",
                email = "hong@example.com",
            ),
            createUserUseCase.receivedCommand,
        )

        assertEquals(
            "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
            result.id,
        )
        assertEquals("홍길동", result.name)
        assertEquals("hong@example.com", result.email)
    }

    @Test
    fun duplicateEmailExceptionTest() {
        // given
        val controller = UserMutationController(
            createUserUseCase =
                DuplicateEmailCreateUserUseCase(),
        )

        val input = CreateUserInput(
            name = "홍길동",
            email = "hong@example.com",
        )

        // when
        val exception =
            assertThrows<DuplicateEmailException> {
                controller.createUser(input)
            }

        // then
        assertEquals(
            "이미 등록된 이메일입니다.",
            exception.message,
        )
    }

    private class RecordingCreateUserUseCase(
        private val result: User,
    ) : CreateUserUseCase {

        var receivedCommand: CreateUserCommand? = null
            private set

        override fun createUser(
            command: CreateUserCommand,
        ): User {
            receivedCommand = command

            return result
        }
    }

    private class DuplicateEmailCreateUserUseCase :
        CreateUserUseCase {

        override fun createUser(
            command: CreateUserCommand,
        ): User {
            throw DuplicateEmailException()
        }
    }
}