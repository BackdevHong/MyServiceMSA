package com.zeronsoftn.honginsung.userservice.application.service

import com.zeronsoftn.honginsung.userservice.application.port.output.UserRepositoryPort
import com.zeronsoftn.honginsung.userservice.domain.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.UUID

class GetUserServiceTest {

    @Test
    fun findUserByIdTest() {
        // given
        val userId = UUID.fromString(
            "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
        )

        val savedUser = User.restore(
            id = userId,
            name = "홍길동",
            email = "hong@example.com",
        )

        val repository = FakeUserRepository(
            users = mutableListOf(savedUser),
        )

        val service = GetUserService(
            userRepositoryPort = repository,
        )

        // when
        val result = service.getUser(userId)

        // then
        assertEquals(userId, repository.requestedUserId)
        assertEquals(userId, result?.id)
        assertEquals("홍길동", result?.name)
        assertEquals("hong@example.com", result?.email)
    }

    @Test
    fun missingUserTest() {
        // given
        val repository = FakeUserRepository()

        val service = GetUserService(
            userRepositoryPort = repository,
        )

        val missingUserId = UUID.fromString(
            "11111111-1111-1111-1111-111111111111",
        )

        // when
        val result = service.getUser(missingUserId)

        // then
        assertEquals(
            missingUserId,
            repository.requestedUserId,
        )
        assertNull(result)
    }

    @Test
    fun findAllUsersTest() {
        // given
        val firstUser = User.restore(
            id = UUID.fromString(
                "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
            ),
            name = "홍길동",
            email = "hong@example.com",
        )

        val secondUser = User.restore(
            id = UUID.fromString(
                "530c167b-98f4-4af2-a23a-f2d96aa4bedd",
            ),
            name = "김철수",
            email = "kim@example.com",
        )

        val repository = FakeUserRepository(
            users = mutableListOf(
                firstUser,
                secondUser,
            ),
        )

        val service = GetUserService(
            userRepositoryPort = repository,
        )

        // when
        val result = service.getUsers()

        // then
        assertEquals(1, repository.findAllCallCount)
        assertEquals(2, result.size)

        assertEquals("홍길동", result[0].name)
        assertEquals("hong@example.com", result[0].email)

        assertEquals("김철수", result[1].name)
        assertEquals("kim@example.com", result[1].email)
    }

    private class FakeUserRepository(
        private val users: MutableList<User> =
            mutableListOf(),
    ) : UserRepositoryPort {

        var requestedUserId: UUID? = null
            private set

        var findAllCallCount: Int = 0
            private set

        override fun existsByEmail(email: String): Boolean {
            return users.any { user ->
                user.email == email
            }
        }

        override fun save(user: User): User {
            users.add(user)
            return user
        }

        override fun findById(id: UUID): User? {
            requestedUserId = id

            return users.find { user ->
                user.id == id
            }
        }

        override fun findAll(): List<User> {
            findAllCallCount += 1

            return users.toList()
        }
    }
}