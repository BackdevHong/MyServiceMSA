package com.zeronsoftn.honginsung.userservice.adapter.input.event

import com.zeronsoftn.honginsung.userservice.application.port.output.UserCreatedMessagePublisherPort
import com.zeronsoftn.honginsung.userservice.domain.event.UserCreatedDomainEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class UserCreatedAfterCommitListener(
    private val userCreatedMessagePublisherPort: UserCreatedMessagePublisherPort
) {
    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT,
        fallbackExecution = false
    )
    fun handle(event: UserCreatedDomainEvent) {
        userCreatedMessagePublisherPort.publish(event)
    }
}