package com.zeronsoftn.honginsung.notificationservice.adapter.input.messaging

data class UserCreatedMessage(
    val metadata: UserCreatedMessageMetadata,
    val payload: UserCreatedMessagePayload,
)

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