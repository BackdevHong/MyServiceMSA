package com.zeronsoftn.honginsung.userservice.application.service

import com.zeronsoftn.honginsung.userservice.application.port.input.GetUserUseCase
import com.zeronsoftn.honginsung.userservice.application.port.output.UserRepositoryPort
import com.zeronsoftn.honginsung.userservice.domain.model.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class GetUserService(
    private val userRepositoryPort: UserRepositoryPort
) : GetUserUseCase {

    @Transactional(readOnly = true)
    override fun getUser(id: UUID): User? {
        return userRepositoryPort.findById(id)
    }

    @Transactional(readOnly = true)
    override fun getUsers(): List<User> {
        return userRepositoryPort.findAll()
    }
}