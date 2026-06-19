package com.zeronsoftn.honginsung.notificationservice.application.service

import com.zeronsoftn.honginsung.notificationservice.application.port.input.SendWelcomeEmailCommand
import com.zeronsoftn.honginsung.notificationservice.application.port.output.EmailSenderPort
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class SendWelcomeEmailServiceTest {

    @Test
    fun sendWelcomeEmailTest() {
        // given
        val emailSender =
            RecordingEmailSender()

        val service = SendWelcomeEmailService(
            emailSenderPort = emailSender,
        )

        val command = SendWelcomeEmailCommand(
            userId = UUID.fromString(
                "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
            ),
            name = "홍길동",
            email = "hong@example.com",
        )

        // when
        service.sendWelcomeEmail(command)

        // then
        assertEquals(
            1,
            emailSender.sentEmails.size,
        )

        val sentEmail =
            emailSender.sentEmails.single()

        assertEquals(command, sentEmail)
    }

    private class RecordingEmailSender :
        EmailSenderPort {

        val sentEmails =
            mutableListOf<SendWelcomeEmailCommand>()

        override fun sendWelcomeEmail(
            userId: UUID,
            name: String,
            email: String,
        ) {
            sentEmails.add(
                SendWelcomeEmailCommand(
                    userId = userId,
                    name = name,
                    email = email,
                ),
            )
        }
    }
}