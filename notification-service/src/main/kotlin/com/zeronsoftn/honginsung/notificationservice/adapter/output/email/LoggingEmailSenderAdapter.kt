package com.zeronsoftn.honginsung.notificationservice.adapter.output.email

import com.zeronsoftn.honginsung.notificationservice.application.port.output.EmailSenderPort
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class LoggingEmailSenderAdapter : EmailSenderPort {

    private val logger =
        LoggerFactory.getLogger(javaClass)

    override fun sendWelcomeEmail(
        userId: UUID,
        name: String,
        email: String,
    ) {
        logger.info(
            "이메일 발송 완료: userId={}, name={}, email={}",
            userId,
            name,
            email,
        )
    }
}