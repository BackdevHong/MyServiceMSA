package com.zeronsoftn.honginsung.notificationservice.application.service

import com.zeronsoftn.honginsung.notificationservice.application.port.input.SendWelcomeEmailCommand
import com.zeronsoftn.honginsung.notificationservice.application.port.input.SendWelcomeEmailUseCase
import com.zeronsoftn.honginsung.notificationservice.application.port.output.EmailSenderPort
import org.springframework.stereotype.Service

@Service
class SendWelcomeEmailService(
    private val emailSenderPort: EmailSenderPort,
) : SendWelcomeEmailUseCase {

    override fun sendWelcomeEmail(
        command: SendWelcomeEmailCommand,
    ) {
        emailSenderPort.sendWelcomeEmail(
            userId = command.userId,
            name = command.name,
            email = command.email,
        )
    }
}