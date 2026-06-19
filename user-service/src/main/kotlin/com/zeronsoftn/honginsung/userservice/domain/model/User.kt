package com.zeronsoftn.honginsung.userservice.domain.model

import com.zeronsoftn.honginsung.userservice.domain.exception.InvalidEmailException
import com.zeronsoftn.honginsung.userservice.domain.exception.InvalidUserNameException
import java.util.Locale
import java.util.UUID

class User private constructor(
    val id: UUID,
    val name: String,
    val email: String,
) {
    companion object {
        private val EMAIL_PATTERN = Regex("""^[^\s@]+@[^\s@]+\.[^\s@]+$""")

        fun create(name: String, email: String): User {
            return createValidated(
                id = UUID.randomUUID(),
                name = name,
                email = email,
            );
        }

        fun restore(id: UUID, name: String, email: String): User {
            return createValidated(
                id = id,
                name = name,
                email = email,
            );
        }

        private fun createValidated(id: UUID, name: String, email: String): User {
            val normalizedName = name.trim()

            if (normalizedName.isBlank()) {
                throw InvalidUserNameException()
            }

            val normalizedEmail = email.trim().lowercase(Locale.ROOT)

            if (!EMAIL_PATTERN.matches(normalizedEmail)) {
                throw InvalidEmailException()
            }

            return User(
                id = id,
                name = normalizedName,
                email = normalizedEmail,
            )
        }
    }
}