package com.zeronsoftn.honginsung.userservice.adapter.output.persistence

import com.zeronsoftn.honginsung.userservice.application.port.output.UserRepositoryPort
import com.zeronsoftn.honginsung.userservice.domain.model.User
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserPersistenceAdapter(
    private val springDataUserRepository: SpringDataUserRepository,
) : UserRepositoryPort {
    override fun existsByEmail(email: String): Boolean {
        return springDataUserRepository.existsByEmail(email)
    }

    override fun save(user: User): User {
        val entity = UserJpaEntity.from(user)

        val savedEntity = springDataUserRepository.save(entity)

        return savedEntity.toDomain()
    }

    override fun findById(id: UUID): User? {
        return springDataUserRepository
            .findById(id)
            .map { entity -> entity.toDomain() }
            .orElse(null)
    }

    override fun findAll(): List<User> {
        return springDataUserRepository
            .findAll()
            .map { entity -> entity.toDomain() }
    }
}