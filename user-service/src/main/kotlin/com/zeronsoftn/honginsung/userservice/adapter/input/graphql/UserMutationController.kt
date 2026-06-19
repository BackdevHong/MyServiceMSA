package com.zeronsoftn.honginsung.userservice.adapter.input.graphql

import com.zeronsoftn.honginsung.userservice.application.port.input.CreateUserUseCase
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class UserMutationController(
    private val createUserUseCase: CreateUserUseCase
) {
    @MutationMapping
    fun createUser(
        @Argument("input") input: CreateUserInput,
    ) : UserPayload {
        val command = input.toCommand()

        val createdUser =
            createUserUseCase.createUser(command)

        return UserPayload.from(createdUser)
    }
}