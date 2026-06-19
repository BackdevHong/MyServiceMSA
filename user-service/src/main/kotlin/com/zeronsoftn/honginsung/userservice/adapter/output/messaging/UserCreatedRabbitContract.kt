package com.zeronsoftn.honginsung.userservice.adapter.output.messaging

object UserCreatedRabbitContract {
    const val EXCHANGE = "myservice.events"
    const val ROUTING_KEY = "user.created.v1"

    const val EVENT_TYPE = "user.created"
    const val EVENT_VERSION = 1
    const val PRODUCER = "user-service"
}