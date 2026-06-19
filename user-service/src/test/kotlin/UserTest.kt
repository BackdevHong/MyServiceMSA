import com.zeronsoftn.honginsung.userservice.domain.exception.InvalidEmailException
import com.zeronsoftn.honginsung.userservice.domain.exception.InvalidUserNameException
import com.zeronsoftn.honginsung.userservice.domain.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class UserTest {

    @Test
    fun createUser() {
        // given
        val name = "홍길동"
        val email = "hong@example.com"

        // when
        val user = User.create(
            name = name,
            email = email,
        )

        // then
        assertNotNull(user.id)
        assertEquals("홍길동", user.name)
        assertEquals("hong@example.com", user.email)
    }

    @Test
    fun removeNameGap() {
        // when
        val user = User.create(
            name = "  홍길동  ",
            email = "hong@example.com",
        )

        // then
        assertEquals("홍길동", user.name)
    }

    @Test
    fun removeEmailGapAndLowerCase() {
        // when
        val user = User.create(
            name = "홍길동",
            email = "  Hong@Example.COM  ",
        )

        // then
        assertEquals("hong@example.com", user.email)
    }

    @Test
    fun throwErrorNullNameTest() {
        // when
        val exception = assertThrows<InvalidUserNameException> {
            User.create(
                name = "   ",
                email = "hong@example.com",
            )
        }

        // then
        assertEquals(
            "사용자 이름은 비어 있을 수 없습니다.",
            exception.message,
        )
    }

    @Test
    fun throwErrorInvalidEmailTest() {
        // when
        val exception = assertThrows<InvalidEmailException> {
            User.create(
                name = "홍길동",
                email = "invalid-email",
            )
        }

        // then
        assertEquals(
            "올바른 이메일 형식이 아닙니다.",
            exception.message,
        )
    }

    @Test
    fun restoreDataTest() {
        // given
        val savedUserId =
            UUID.fromString("14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb")

        // when
        val user = User.restore(
            id = savedUserId,
            name = "홍길동",
            email = "hong@example.com",
        )

        // then
        assertEquals(savedUserId, user.id)
        assertEquals("홍길동", user.name)
        assertEquals("hong@example.com", user.email)
    }
}