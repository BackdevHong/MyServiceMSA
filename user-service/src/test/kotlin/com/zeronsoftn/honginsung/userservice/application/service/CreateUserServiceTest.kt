package com.zeronsoftn.honginsung.userservice.application.service

import com.zeronsoftn.honginsung.userservice.application.port.input.CreateUserCommand
import com.zeronsoftn.honginsung.userservice.application.port.output.UserCreatedEventPublisherPort
import com.zeronsoftn.honginsung.userservice.application.port.output.UserRepositoryPort
import com.zeronsoftn.honginsung.userservice.domain.event.UserCreatedDomainEvent
import com.zeronsoftn.honginsung.userservice.domain.exception.DuplicateEmailException
import com.zeronsoftn.honginsung.userservice.domain.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class CreateUserServiceTest {
    @Test
    fun saveNewUserAndPublishEvent() {
        // given
        val callOrder = mutableListOf<String>()

        val repository = FakeUserRepository(
            callOrder = callOrder,
        )

        val eventPublisher = FakeUserCreatedEventPublisher(
            callOrder = callOrder,
        )

        val service = CreateUserService(
            userRepositoryPort = repository,
            userCreatedEventPublisherPort = eventPublisher,
        )

        val command = CreateUserCommand(
            name = "홍길동",
            email = "Hong@Example.COM",
        )

        // when
        val result = service.createUser(command)

        // then
        assertEquals(1, repository.savedUsers.size)
        assertEquals(result.id, repository.savedUsers.single().id)
        assertEquals("홍길동", result.name)
        assertEquals("hong@example.com", result.email)

        assertEquals(1, eventPublisher.publishedEvents.size)

        val publishedEvent = eventPublisher.publishedEvents.single()

        assertEquals(result.id, publishedEvent.userId)
        assertEquals("홍길동", publishedEvent.name)
        assertEquals("hong@example.com", publishedEvent.email)

        // 저장 호출 이후 이벤트 발행이 호출됐는지 확인
        assertEquals(
            listOf("save", "publish"),
            callOrder,
        )
    }

    @Test
    fun checkDuplicateEmailThenNoPublishEvent() {
        // given
        val repository = FakeUserRepository(
            existingEmails = mutableSetOf("hong@example.com"),
        )

        val eventPublisher = FakeUserCreatedEventPublisher()

        val service = CreateUserService(
            userRepositoryPort = repository,
            userCreatedEventPublisherPort = eventPublisher,
        )

        val command = CreateUserCommand(
            name = "홍길동",
            email = "HONG@EXAMPLE.COM",
        )

        // when
        val exception = assertThrows<DuplicateEmailException> {
            service.createUser(command)
        }

        // then
        assertEquals(
            "이미 등록된 이메일입니다.",
            exception.message,
        )

        assertTrue(repository.savedUsers.isEmpty())
        assertTrue(eventPublisher.publishedEvents.isEmpty())
    }

    @Test
    fun ifFailSavedUserThenNoPublishEvent() {
        // given
        val saveException = IllegalStateException("DB 저장 실패")

        val repository = FakeUserRepository(
            saveException = saveException,
        )

        val eventPublisher = FakeUserCreatedEventPublisher()

        val service = CreateUserService(
            userRepositoryPort = repository,
            userCreatedEventPublisherPort = eventPublisher,
        )

        val command = CreateUserCommand(
            name = "홍길동",
            email = "hong@example.com",
        )

        // when
        val thrownException = assertThrows<IllegalStateException> {
            service.createUser(command)
        }

        // then
        assertSame(saveException, thrownException)
        assertTrue(repository.savedUsers.isEmpty())
        assertTrue(eventPublisher.publishedEvents.isEmpty())
    }

    /**
     * 테스트 레포지토리
     */
    private class FakeUserRepository(
        private val existingEmails: MutableSet<String> = mutableSetOf(),
        private val saveException: RuntimeException? = null,
        private val callOrder: MutableList<String> = mutableListOf(),
    ) : UserRepositoryPort {

        val savedUsers = mutableListOf<User>()

        override fun existsByEmail(email: String): Boolean {
            return email in existingEmails ||
                    savedUsers.any { savedUser ->
                        savedUser.email == email
                    }
        }

        override fun save(user: User): User {
            saveException?.let { throw it }

            callOrder.add("save")
            savedUsers.add(user)

            return user
        }

        override fun findById(id: UUID): User? {
            return savedUsers.find { user ->
                user.id == id
            }
        }

        override fun findAll(): List<User> {
            return savedUsers.toList()
        }
    }

    private class FakeUserCreatedEventPublisher(
        private val callOrder: MutableList<String> = mutableListOf(),
    ) : UserCreatedEventPublisherPort {

        val publishedEvents =
            mutableListOf<UserCreatedDomainEvent>()

        override fun publish(event: UserCreatedDomainEvent) {
            callOrder.add("publish")
            publishedEvents.add(event)
        }
    }
}