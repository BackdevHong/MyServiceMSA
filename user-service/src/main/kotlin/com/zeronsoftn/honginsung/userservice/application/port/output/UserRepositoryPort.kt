package com.zeronsoftn.honginsung.userservice.application.port.output

import com.zeronsoftn.honginsung.userservice.domain.model.User

interface UserRepositoryPort {

    fun existsByEmail(email: String): Boolean

    fun save(user: User): User
}