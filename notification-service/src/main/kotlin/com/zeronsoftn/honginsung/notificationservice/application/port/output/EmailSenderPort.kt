package com.zeronsoftn.honginsung.notificationservice.application.port.output

import java.util.UUID

interface EmailSenderPort {

    fun sendWelcomeEmail(
        userId: UUID,
        name: String,
        email: String,
    )
}