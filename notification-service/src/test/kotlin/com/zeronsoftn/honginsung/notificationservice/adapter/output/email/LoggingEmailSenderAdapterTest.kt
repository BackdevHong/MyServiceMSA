package com.zeronsoftn.honginsung.notificationservice.adapter.output.email

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import java.util.UUID

@ExtendWith(OutputCaptureExtension::class)
class LoggingEmailSenderAdapterTest {

    @Test
    fun logEmailSentTest(
        output: CapturedOutput,
    ) {
        // given
        val adapter =
            LoggingEmailSenderAdapter()

        // when
        adapter.sendWelcomeEmail(
            userId = UUID.fromString(
                "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
            ),
            name = "홍길동",
            email = "hong@example.com",
        )

        // then
        assertTrue(
            output.out.contains(
                "이메일 발송 완료",
            ),
        )

        assertTrue(
            output.out.contains(
                "hong@example.com",
            ),
        )
    }
}