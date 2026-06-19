package com.zeronsoftn.honginsung.userservice.adapter.output.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import com.zeronsoftn.honginsung.userservice.application.port.output.UserCreatedMessagePublisherPort
import com.zeronsoftn.honginsung.userservice.domain.event.UserCreatedDomainEvent
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageDeliveryMode
import org.springframework.amqp.rabbit.core.RabbitOperations
import org.springframework.amqp.core.MessageProperties
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.Date

@Component
class RabbitUserCreatedMessagePublisherAdapter(
    private val rabbitOperations: RabbitOperations,
    private val objectMapper: ObjectMapper
) : UserCreatedMessagePublisherPort {

    override fun publish(event: UserCreatedDomainEvent) {
        val eventMessage =
            UserCreatedMessage.from(event)

        val jsonBody = objectMapper.writeValueAsBytes(eventMessage)

        val messageProperties = MessageProperties().apply {
            contentType = MessageProperties.CONTENT_TYPE_JSON
            contentEncoding = StandardCharsets.UTF_8.name()
            deliveryMode = MessageDeliveryMode.PERSISTENT
            messageId = event.eventId.toString()
            timestamp = Date.from(event.occurredAt)
            type = UserCreatedRabbitContract.ROUTING_KEY
            setHeader("eventId", event.eventId.toString())
            setHeader("eventType", UserCreatedRabbitContract.EVENT_TYPE)
            setHeader("eventVersion", UserCreatedRabbitContract.EVENT_VERSION)
        }

        val message = Message(
            jsonBody,
            messageProperties
        )

        rabbitOperations.send(
            UserCreatedRabbitContract.EXCHANGE,
            UserCreatedRabbitContract.ROUTING_KEY,
            message
        )
    }

}