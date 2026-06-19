package com.zeronsoftn.honginsung.userservice.adapter.input.event

import com.zeronsoftn.honginsung.userservice.application.port.output.UserCreatedMessagePublisherPort
import com.zeronsoftn.honginsung.userservice.domain.event.UserCreatedDomainEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.time.Instant
import java.util.UUID

class UserCreatedAfterCommitListenerTest {

    @Test
    fun publishMessageTest() {
        // given
        val publisher =
            RecordingUserCreatedMessagePublisher()

        val listener =
            UserCreatedAfterCommitListener(
                userCreatedMessagePublisherPort = publisher,
            )

        val event = createEvent()

        // when
        listener.handle(event)

        // then
        assertEquals(1, publisher.events.size)
        assertSame(event, publisher.events.single())
    }

    @Test
    fun afterCommitAnnotationTest() {
        // given
        val handleMethod =
            UserCreatedAfterCommitListener::class.java
                .getDeclaredMethod(
                    "handle",
                    UserCreatedDomainEvent::class.java,
                )

        // when
        val annotation =
            handleMethod.getAnnotation(
                TransactionalEventListener::class.java,
            ) ?: error(
                "TransactionalEventListener가 필요합니다.",
            )

        // then
        assertEquals(
            TransactionPhase.AFTER_COMMIT,
            annotation.phase,
        )

        assertFalse(annotation.fallbackExecution)
    }

    private fun createEvent(): UserCreatedDomainEvent {
        return UserCreatedDomainEvent(
            eventId = UUID.fromString(
                "8fe55190-c136-43ee-bcd1-1ece6414b1e7",
            ),
            userId = UUID.fromString(
                "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
            ),
            name = "홍길동",
            email = "hong@example.com",
            occurredAt =
                Instant.parse("2026-06-19T06:00:00Z"),
        )
    }

    private class RecordingUserCreatedMessagePublisher :
        UserCreatedMessagePublisherPort {

        val events =
            mutableListOf<UserCreatedDomainEvent>()

        override fun publish(
            event: UserCreatedDomainEvent,
        ) {
            events.add(event)
        }
    }
}