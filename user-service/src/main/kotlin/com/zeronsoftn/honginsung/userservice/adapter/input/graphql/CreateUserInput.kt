package com.zeronsoftn.honginsung.userservice.adapter.input.graphql

import com.zeronsoftn.honginsung.userservice.application.port.input.CreateUserCommand

data class CreateUserInput(
    val name: String,
    val email: String,
) {
    fun toCommand() : CreateUserCommand {
        return CreateUserCommand(
            name = name,
            email = email
        )
    }
}