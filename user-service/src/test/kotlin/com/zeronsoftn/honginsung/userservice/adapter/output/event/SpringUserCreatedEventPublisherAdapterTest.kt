package com.zeronsoftn.honginsung.userservice.adapter.output.event

import com.zeronsoftn.honginsung.userservice.domain.event.UserCreatedDomainEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher
import java.time.Instant
import java.util.UUID

class SpringUserCreatedEventPublisherAdapterTest {
    @Test
    fun publishEventTest() {
        // given
        val applicationEventPublisher =
            RecordingApplicationEventPublisher()

        val adapter =
            SpringUserCreatedEventPublisherAdapter(
                applicationEventPublisher = applicationEventPublisher,
            )

        val event = UserCreatedDomainEvent(
            eventId = UUID.fromString(
                "8fe55190-c136-43ee-bcd1-1ece6414b1e7",
            ),
            userId = UUID.fromString(
                "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
            ),
            name = "홍길동",
            email = "hong@example.com",
            occurredAt = Instant.parse(
                "2026-06-19T06:00:00Z",
            ),
        )

        // when
        adapter.publish(event)

        // then
        assertEquals(
            1,
            applicationEventPublisher.publishedEvents.size,
        )

        assertSame(
            event,
            applicationEventPublisher.publishedEvents.single(),
        )
    }

    private class RecordingApplicationEventPublisher :
        ApplicationEventPublisher {

        val publishedEvents = mutableListOf<Any>()

        override fun publishEvent(event: Any) {
            publishedEvents.add(event)
        }
    }
}