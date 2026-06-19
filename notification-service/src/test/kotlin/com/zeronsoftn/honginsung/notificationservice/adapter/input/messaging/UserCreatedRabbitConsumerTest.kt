package com.zeronsoftn.honginsung.notificationservice.adapter.input.messaging

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.zeronsoftn.honginsung.notificationservice.application.port.input.SendWelcomeEmailCommand
import com.zeronsoftn.honginsung.notificationservice.application.port.input.SendWelcomeEmailUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageProperties
import java.nio.charset.StandardCharsets
import java.util.UUID

class UserCreatedRabbitConsumerTest {

    @Test
    fun consumeUserCreatedMessageTest() {
        // given
        val useCase =
            RecordingSendWelcomeEmailUseCase()

        val consumer = UserCreatedRabbitConsumer(
            objectMapper = jacksonObjectMapper(),
            sendWelcomeEmailUseCase = useCase,
        )

        val json = """
            {
              "metadata": {
                "eventId": "8fe55190-c136-43ee-bcd1-1ece6414b1e7",
                "eventType": "user.created",
                "eventVersion": 1,
                "occurredAt": "2026-06-19T06:00:00Z",
                "producer": "user-service"
              },
              "payload": {
                "userId": "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
                "name": "홍길동",
                "email": "hong@example.com"
              }
            }
        """.trimIndent()

        val message = Message(
            json.toByteArray(StandardCharsets.UTF_8),
            MessageProperties(),
        )

        // when
        consumer.consume(message)

        // then
        assertEquals(
            1,
            useCase.commands.size,
        )

        val command =
            useCase.commands.single()

        assertEquals(
            UUID.fromString(
                "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
            ),
            command.userId,
        )

        assertEquals("홍길동", command.name)
        assertEquals(
            "hong@example.com",
            command.email,
        )
    }

    private class RecordingSendWelcomeEmailUseCase :
        SendWelcomeEmailUseCase {

        val commands =
            mutableListOf<SendWelcomeEmailCommand>()

        override fun sendWelcomeEmail(
            command: SendWelcomeEmailCommand,
        ) {
            commands.add(command)
        }
    }
}