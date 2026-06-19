package com.zeronsoftn.honginsung.userservice.adapter.input.graphql

import com.zeronsoftn.honginsung.userservice.domain.model.User

data class UserPayload(
    val id: String,
    val name: String,
    val email: String,
) {
    companion object {
        fun from(user: User): UserPayload {
            return UserPayload(
                id = user.id.toString(),
                name = user.name,
                email = user.email,
            )
        }
    }
}