package com.zeronsoftn.honginsung.userservice.adapter.output.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import com.zeronsoftn.honginsung.userservice.domain.event.UserCreatedDomainEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageDeliveryMode
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.core.RabbitOperations
import java.time.Instant
import java.util.Date
import java.util.UUID

class RabbitUserCreatedMessagePublisherAdapterTest {

    @Test
    fun publishUserCreatedMessageTest() {
        // given
        val rabbitOperations =
            mock(RabbitOperations::class.java)

        val objectMapper = ObjectMapper()

        val adapter =
            RabbitUserCreatedMessagePublisherAdapter(
                rabbitOperations = rabbitOperations,
                objectMapper = objectMapper,
            )

        val occurredAt =
            Instant.parse("2026-06-19T06:00:00Z")

        val event = UserCreatedDomainEvent(
            eventId = UUID.fromString(
                "8fe55190-c136-43ee-bcd1-1ece6414b1e7",
            ),
            userId = UUID.fromString(
                "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
            ),
            name = "홍길동",
            email = "hong@example.com",
            occurredAt = occurredAt,
        )

        val messageCaptor =
            ArgumentCaptor.forClass(Message::class.java)

        // when
        adapter.publish(event)

        // then
        verify(rabbitOperations).send(
            eq(UserCreatedRabbitContract.EXCHANGE),
            eq(UserCreatedRabbitContract.ROUTING_KEY),
            messageCaptor.capture(),
        )

        val sentMessage = messageCaptor.value

        val properties =
            sentMessage.messageProperties

        assertEquals(
            MessageProperties.CONTENT_TYPE_JSON,
            properties.contentType,
        )

        assertEquals(
            "UTF-8",
            properties.contentEncoding,
        )

        assertEquals(
            MessageDeliveryMode.PERSISTENT,
            properties.deliveryMode,
        )

        assertEquals(
            event.eventId.toString(),
            properties.messageId,
        )

        assertEquals(
            Date.from(occurredAt),
            properties.timestamp,
        )

        assertEquals(
            "user.created.v1",
            properties.type,
        )

        assertEquals(
            event.eventId.toString(),
            properties.headers["eventId"],
        )

        assertEquals(
            "user.created",
            properties.headers["eventType"],
        )

        assertEquals(
            1,
            properties.headers["eventVersion"],
        )

        val json =
            objectMapper.readTree(sentMessage.body)

        assertEquals(
            event.eventId.toString(),
            json["metadata"]["eventId"].asText(),
        )

        assertEquals(
            "user.created",
            json["metadata"]["eventType"].asText(),
        )

        assertEquals(
            1,
            json["metadata"]["eventVersion"].asInt(),
        )

        assertEquals(
            "2026-06-19T06:00:00Z",
            json["metadata"]["occurredAt"].asText(),
        )

        assertEquals(
            "user-service",
            json["metadata"]["producer"].asText(),
        )

        assertEquals(
            event.userId.toString(),
            json["payload"]["userId"].asText(),
        )

        assertEquals(
            "홍길동",
            json["payload"]["name"].asText(),
        )

        assertEquals(
            "hong@example.com",
            json["payload"]["email"].asText(),
        )
    }
}