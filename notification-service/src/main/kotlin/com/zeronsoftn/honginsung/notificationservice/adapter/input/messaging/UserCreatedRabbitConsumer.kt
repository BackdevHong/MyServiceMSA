package com.zeronsoftn.honginsung.notificationservice.adapter.input.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import com.zeronsoftn.honginsung.notificationservice.application.port.input.SendWelcomeEmailCommand
import com.zeronsoftn.honginsung.notificationservice.application.port.input.SendWelcomeEmailUseCase
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserCreatedRabbitConsumer(
    private val objectMapper: ObjectMapper,
    private val sendWelcomeEmailUseCase:
    SendWelcomeEmailUseCase,
) {

    @RabbitListener(
        queues = [UserCreatedRabbitContract.QUEUE],
    )
    fun consume(message: Message) {
        val userCreatedMessage =
            objectMapper.readValue(
                message.body,
                UserCreatedMessage::class.java,
            )

        val payload =
            userCreatedMessage.payload

        val command = SendWelcomeEmailCommand(
            userId = UUID.fromString(payload.userId),
            name = payload.name,
            email = payload.email,
        )

        sendWelcomeEmailUseCase
            .sendWelcomeEmail(command)
    }
}