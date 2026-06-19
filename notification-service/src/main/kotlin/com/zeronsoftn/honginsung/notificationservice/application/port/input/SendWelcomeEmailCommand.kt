package com.zeronsoftn.honginsung.notificationservice.application.port.input

import java.util.UUID

data class SendWelcomeEmailCommand(
    val userId: UUID,
    val name: String,
    val email: String,
)