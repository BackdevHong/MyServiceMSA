package com.zeronsoftn.honginsung.userservice.application.port.input

data class CreateUserCommand(
    val name: String,
    val email: String,
) {
}