package com.zeronsoftn.honginsung.userservice.application.port.output

import com.zeronsoftn.honginsung.userservice.domain.event.UserCreatedDomainEvent

interface UserCreatedEventPublisherPort {

    fun publish(event: UserCreatedDomainEvent)
}