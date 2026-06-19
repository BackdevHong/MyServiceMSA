package com.zeronsoftn.honginsung.userservice.adapter.output.persistence

import org.springframework.data.repository.Repository
import java.util.Optional
import java.util.UUID

interface SpringDataUserRepository :
    Repository<UserJpaEntity, UUID> {

    fun <S : UserJpaEntity> save(entity: S): S

    fun existsByEmail(email: String): Boolean

    fun findById(id: UUID): Optional<UserJpaEntity>

    fun findAll(): List<UserJpaEntity>
}