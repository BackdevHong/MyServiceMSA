package com.zeronsoftn.honginsung.userservice.domain.event

import com.zeronsoftn.honginsung.userservice.domain.model.User
import java.time.Instant
import java.util.UUID

data class UserCreatedDomainEvent(
    val eventId: UUID,
    val userId: UUID,
    val name: String,
    val email: String,
    val occurredAt: Instant
) {
    companion object {
        fun from(user: User) : UserCreatedDomainEvent {
            return UserCreatedDomainEvent(
                eventId = UUID.randomUUID(),
                userId = user.id,
                name = user.name,
                email = user.email,
                occurredAt = Instant.now()
            )
        }
    }
}