package com.zeronsoftn.honginsung.notificationservice.application.port.input

interface SendWelcomeEmailUseCase {

    fun sendWelcomeEmail(
        command: SendWelcomeEmailCommand,
    )
}