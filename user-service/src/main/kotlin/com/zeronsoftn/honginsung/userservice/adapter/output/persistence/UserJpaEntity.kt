package com.zeronsoftn.honginsung.userservice.adapter.output.persistence

import com.zeronsoftn.honginsung.userservice.domain.model.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.persistence.Version
import java.util.UUID

@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_users_email",
            columnNames = ["email"],
        ),
    ],
)
open class UserJpaEntity(
    @field:Id
    @field:Column(
        name = "id",
        nullable = false,
        updatable = false,
    )
    private var id: UUID,

    @field:Column(
        name = "name",
        nullable = false,
        length = 100
    )
    private var name: String,

    @field:Column(
        name = "email",
        nullable = false,
        length = 320
    )
    private var email: String,

    @field:Version
    private var version: Long? = null,
) {

    fun toDomain(): User {
        return User.restore(
            id = id,
            name = name,
            email = email,
        )
    }

    companion object {
        fun from(user: User): UserJpaEntity {
            return UserJpaEntity(
                id = user.id,
                name = user.name,
                email = user.email
            )
        }
    }
}