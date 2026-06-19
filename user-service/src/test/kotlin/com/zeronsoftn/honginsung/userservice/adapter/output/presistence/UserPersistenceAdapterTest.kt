package com.zeronsoftn.honginsung.userservice.adapter.output.presistence

import com.zeronsoftn.honginsung.userservice.adapter.output.persistence.SpringDataUserRepository
import com.zeronsoftn.honginsung.userservice.adapter.output.persistence.UserJpaEntity
import com.zeronsoftn.honginsung.userservice.adapter.output.persistence.UserPersistenceAdapter
import com.zeronsoftn.honginsung.userservice.domain.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import java.util.Optional
import java.util.UUID

class UserPersistenceAdapterTest {

    @Test
    fun saveUserTest() {
        val repository = FakeSpringDataUserRepository()

        val adapter = UserPersistenceAdapter(
            springDataUserRepository = repository,
        )

        val user = User.restore(
            id = UUID.fromString(
                "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
            ),
            name = "홍길동",
            email = "hong@example.com",
        )

        val result = adapter.save(user)

        assertEquals(1, repository.savedEntities.size)

        val savedEntity =
            repository.savedEntities.single()

        val savedUser = savedEntity.toDomain()

        assertEquals(user.id, savedUser.id)
        assertEquals("홍길동", savedUser.name)
        assertEquals("hong@example.com", savedUser.email)

        assertEquals(user.id, result.id)
        assertEquals(user.name, result.name)
        assertEquals(user.email, result.email)
    }

    @Test
    fun existsByEmailTest() {
        val repository = FakeSpringDataUserRepository(
            existingEmails = setOf(
                "hong@example.com",
            ),
        )

        val adapter = UserPersistenceAdapter(
            springDataUserRepository = repository,
        )

        val exists = adapter.existsByEmail(
            "hong@example.com",
        )

        assertTrue(exists)
        assertEquals(
            "hong@example.com",
            repository.checkedEmail,
        )
    }

    @Test
    fun findUserByIdTest() {
        // given
        val userId = UUID.fromString(
            "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
        )

        val repository = FakeSpringDataUserRepository()

        repository.save(
            UserJpaEntity.from(
                User.restore(
                    id = userId,
                    name = "홍길동",
                    email = "hong@example.com",
                ),
            ),
        )

        val adapter = UserPersistenceAdapter(
            springDataUserRepository = repository,
        )

        // when
        val result = adapter.findById(userId)

        // then
        assertEquals(userId, result?.id)
        assertEquals("홍길동", result?.name)
        assertEquals("hong@example.com", result?.email)
    }

    @Test
    fun missingUserTest() {
        // given
        val repository = FakeSpringDataUserRepository()

        val adapter = UserPersistenceAdapter(
            springDataUserRepository = repository,
        )

        val missingUserId = UUID.fromString(
            "11111111-1111-1111-1111-111111111111",
        )

        // when
        val result = adapter.findById(missingUserId)

        // then
        assertNull(result)
    }

    @Test
    fun findAllUsersTest() {
        // given
        val repository = FakeSpringDataUserRepository()

        repository.save(
            UserJpaEntity.from(
                User.restore(
                    id = UUID.fromString(
                        "14b70b0e-b7d8-4a44-bb3c-c9d87bbb13bb",
                    ),
                    name = "홍길동",
                    email = "hong@example.com",
                ),
            ),
        )

        repository.save(
            UserJpaEntity.from(
                User.restore(
                    id = UUID.fromString(
                        "530c167b-98f4-4af2-a23a-f2d96aa4bedd",
                    ),
                    name = "김철수",
                    email = "kim@example.com",
                ),
            ),
        )

        val adapter = UserPersistenceAdapter(
            springDataUserRepository = repository,
        )

        // when
        val result = adapter.findAll()

        // then
        assertEquals(2, result.size)

        assertEquals("홍길동", result[0].name)
        assertEquals("hong@example.com", result[0].email)

        assertEquals("김철수", result[1].name)
        assertEquals("kim@example.com", result[1].email)
    }

    private class FakeSpringDataUserRepository(
        private val existingEmails: Set<String> = emptySet(),
    ) : SpringDataUserRepository {

        val savedEntities =
            mutableListOf<UserJpaEntity>()

        var checkedEmail: String? = null
            private set

        override fun <S : UserJpaEntity> save(entity: S): S {
            savedEntities.add(entity)

            return entity
        }

        override fun existsByEmail(email: String): Boolean {
            checkedEmail = email

            return existingEmails.contains(email)
        }

        override fun findById(id: UUID): Optional<UserJpaEntity> {
            val entity = savedEntities.find { userEntity ->
                userEntity.toDomain().id == id
            }

            return Optional.ofNullable(entity)
        }

        override fun findAll(): List<UserJpaEntity> {
            return savedEntities.toList()
        }
    }
}