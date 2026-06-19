package com.zeronsoftn.honginsung.userservice.adapter.output.messaging

import com.zeronsoftn.honginsung.userservice.domain.event.UserCreatedDomainEvent

data class UserCreatedMessage(
    val metadata: UserCreatedMessageMetadata,
    val payload: UserCreatedMessagePayload,
) {
    companion object {
        fun from(event: UserCreatedDomainEvent): UserCreatedMessage {
            return UserCreatedMessage(
                metadata = UserCreatedMessageMetadata(
                    eventId = event.eventId.toString(),
                    eventType = UserCreatedRabbitContract.EVENT_TYPE,
                    eventVersion = UserCreatedRabbitContract.EVENT_VERSION,
                    occurredAt = event.occurredAt.toString(),
                    producer = UserCreatedRabbitContract.PRODUCER,
                ),
                payload = UserCreatedMessagePayload(
                    userId = event.userId.toString(),
                    name = event.name,
                    email = event.email,
                )
            )
        }
    }
}

data class UserCreatedMessageMetadata(
    val eventId: String,
    val eventType: String,
    val eventVersion: Int,
    val occurredAt: String,
    val producer: String,
)

data class UserCreatedMessagePayload(
    val userId: String,
    val name: String,
    val email: String,
)