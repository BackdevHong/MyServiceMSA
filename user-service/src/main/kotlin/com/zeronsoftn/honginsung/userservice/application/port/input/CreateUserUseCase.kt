package com.zeronsoftn.honginsung.userservice.application.port.input

import com.zeronsoftn.honginsung.userservice.domain.model.User

interface CreateUserUseCase {

    fun createUser(command: CreateUserCommand): User
}