package com.zeronsoftn.honginsung.userservice.application.port.input

import com.zeronsoftn.honginsung.userservice.domain.model.User
import java.util.UUID

interface GetUserUseCase {
    fun getUser(id: UUID): User?

    fun getUsers(): List<User>
}