package com.zeronsoftn.honginsung.userservice.adapter.output.event

import com.zeronsoftn.honginsung.userservice.application.port.output.UserCreatedEventPublisherPort
import com.zeronsoftn.honginsung.userservice.domain.event.UserCreatedDomainEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class SpringUserCreatedEventPublisherAdapter(
    private val applicationEventPublisher: ApplicationEventPublisher
) : UserCreatedEventPublisherPort {

    override fun publish(event: UserCreatedDomainEvent) {
        applicationEventPublisher.publishEvent(event)
    }

}