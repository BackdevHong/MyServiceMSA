package com.zeronsoftn.honginsung.userservice.adapter.input.graphql

import com.zeronsoftn.honginsung.userservice.application.port.input.GetUserUseCase
import com.zeronsoftn.honginsung.userservice.domain.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.UUID

class UserQueryControllerTest {

    @Test
    fun findUserByIdTest() {
        // given
        val userId = UUID.fromString(
            "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
        )

        val useCase = FakeGetUserUseCase(
            users = listOf(
                User.restore(
                    id = userId,
                    name = "홍길동",
                    email = "hong@example.com",
                ),
            ),
        )

        val controller = UserQueryController(
            getUserUseCase = useCase,
        )

        // when
        val result = controller.user(
            id = userId.toString(),
        )

        // then
        assertEquals(userId, useCase.requestedUserId)

        assertEquals(userId.toString(), result?.id)
        assertEquals("홍길동", result?.name)
        assertEquals("hong@example.com", result?.email)
    }

    @Test
    fun missingUserTest() {
        // given
        val useCase = FakeGetUserUseCase()

        val controller = UserQueryController(
            getUserUseCase = useCase,
        )

        val missingUserId =
            "11111111-1111-1111-1111-111111111111"

        // when
        val result = controller.user(
            id = missingUserId,
        )

        // then
        assertEquals(
            UUID.fromString(missingUserId),
            useCase.requestedUserId,
        )

        assertNull(result)
    }

    @Test
    fun findAllUsersTest() {
        // given
        val useCase = FakeGetUserUseCase(
            users = listOf(
                User.restore(
                    id = UUID.fromString(
                        "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
                    ),
                    name = "홍길동",
                    email = "hong@example.com",
                ),
                User.restore(
                    id = UUID.fromString(
                        "530c167b-98f4-4af2-a23a-f2d96aa4bedd",
                    ),
                    name = "김철수",
                    email = "kim@example.com",
                ),
            ),
        )

        val controller = UserQueryController(
            getUserUseCase = useCase,
        )

        // when
        val result = controller.users()

        // then
        assertEquals(1, useCase.getUsersCallCount)
        assertEquals(2, result.size)

        assertEquals(
            "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
            result[0].id,
        )
        assertEquals("홍길동", result[0].name)
        assertEquals("hong@example.com", result[0].email)

        assertEquals(
            "530c167b-98f4-4af2-a23a-f2d96aa4bedd",
            result[1].id,
        )
        assertEquals("김철수", result[1].name)
        assertEquals("kim@example.com", result[1].email)
    }

    private class FakeGetUserUseCase(
        private val users: List<User> = emptyList(),
    ) : GetUserUseCase {

        var requestedUserId: UUID? = null
            private set

        var getUsersCallCount: Int = 0
            private set

        override fun getUser(id: UUID): User? {
            requestedUserId = id

            return users.find { user ->
                user.id == id
            }
        }

        override fun getUsers(): List<User> {
            getUsersCallCount += 1

            return users.toList()
        }
    }
}