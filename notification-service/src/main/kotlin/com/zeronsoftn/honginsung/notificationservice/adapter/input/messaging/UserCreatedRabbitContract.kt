package com.zeronsoftn.honginsung.notificationservice.adapter.input.messaging

object UserCreatedRabbitContract {

    const val EXCHANGE = "myservice.events"

    const val QUEUE =
        "notification.user-created.v1"

    const val ROUTING_KEY =
        "user.created.v1"
}