package com.zeronsoftn.honginsung.userservice.application.service

import com.zeronsoftn.honginsung.userservice.application.port.input.CreateUserCommand
import com.zeronsoftn.honginsung.userservice.application.port.input.CreateUserUseCase
import com.zeronsoftn.honginsung.userservice.application.port.output.UserCreatedEventPublisherPort
import com.zeronsoftn.honginsung.userservice.application.port.output.UserRepositoryPort
import com.zeronsoftn.honginsung.userservice.domain.event.UserCreatedDomainEvent
import com.zeronsoftn.honginsung.userservice.domain.exception.DuplicateEmailException
import com.zeronsoftn.honginsung.userservice.domain.model.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateUserService(
    private val userRepositoryPort: UserRepositoryPort,
    private val userCreatedEventPublisherPort: UserCreatedEventPublisherPort,
) : CreateUserUseCase {
    @Transactional
    override fun createUser(command: CreateUserCommand): User {
        val newUser = User.create(
            name = command.name,
            email = command.email
        )

        if (userRepositoryPort.existsByEmail(newUser.email)) {
            throw DuplicateEmailException()
        }

        val savedUser = userRepositoryPort.save(newUser)

        userCreatedEventPublisherPort.publish(
            UserCreatedDomainEvent.from(savedUser)
        )

        return savedUser
    }
}