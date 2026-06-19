package com.zeronsoftn.honginsung.userservice.adapter.input.graphql

import com.zeronsoftn.honginsung.userservice.application.port.input.GetUserUseCase
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
class UserQueryController(
    private val getUserUseCase: GetUserUseCase
) {

    @QueryMapping
    fun user(
        @Argument id: String
    ) : UserPayload? {
        return getUserUseCase
            .getUser(UUID.fromString(id))
            ?.let(UserPayload::from)
    }

    @QueryMapping
    fun users() : List<UserPayload> {
        return getUserUseCase
            .getUsers()
            .map(UserPayload::from)
    }
}