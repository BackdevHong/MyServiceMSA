package com.zeronsoftn.honginsung.userservice.domain.model

import com.zeronsoftn.honginsung.userservice.domain.exception.InvalidEmailException
import com.zeronsoftn.honginsung.userservice.domain.exception.InvalidUserNameException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class UserTest {

    @Test
    fun createUser() {
        val name = "홍길동"
        val email = "hong@example.com"

        val user = User.create(
            name = name,
            email = email,
        )

        assertNotNull(user.id)
        Assertions.assertEquals("홍길동", user.name)
        Assertions.assertEquals("hong@example.com", user.email)
    }

    @Test
    fun removeNameGap() {
        val user = User.create(
            name = "  홍길동  ",
            email = "hong@example.com",
        )

        Assertions.assertEquals("홍길동", user.name)
    }

    @Test
    fun removeEmailGapAndLowerCase() {
        val user = User.create(
            name = "홍길동",
            email = "  Hong@Example.COM  ",
        )

        Assertions.assertEquals("hong@example.com", user.email)
    }

    @Test
    fun throwErrorNullNameTest() {
        val exception = assertThrows<InvalidUserNameException> {
            User.create(
                name = "   ",
                email = "hong@example.com",
            )
        }

        Assertions.assertEquals(
            "사용자 이름은 비어 있을 수 없습니다.",
            exception.message,
        )
    }

    @Test
    fun throwErrorInvalidEmailTest() {
        val exception = assertThrows<InvalidEmailException> {
            User.create(
                name = "홍길동",
                email = "invalid-email",
            )
        }

        Assertions.assertEquals(
            "올바른 이메일 형식이 아닙니다.",
            exception.message,
        )
    }

    @Test
    fun restoreDataTest() {
        val savedUserId =
            UUID.fromString("14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb")

        val user = User.restore(
            id = savedUserId,
            name = "홍길동",
            email = "hong@example.com",
        )

        Assertions.assertEquals(savedUserId, user.id)
        Assertions.assertEquals("홍길동", user.name)
        Assertions.assertEquals("hong@example.com", user.email)
    }
}