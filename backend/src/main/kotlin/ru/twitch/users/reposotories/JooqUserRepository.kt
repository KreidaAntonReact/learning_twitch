package ru.twitch.users.reposotories

import org.jooq.DSLContext
import com.example.demo.jooq.Tables.USERS
import com.example.demo.jooq.tables.records.UsersRecord
import org.springframework.stereotype.Repository
import ru.twitch.users.domians.UserEntity
import ru.twitch.users.dto.UserRequestCreateDto
import ru.twitch.users.dto.UserUpdateDto
import ru.twitch.users.utils.UserNotFoundException
import java.time.LocalDateTime
import java.util.UUID

@Repository
class JooqUserRepository(
    private val dsl: DSLContext
) : UserRepository {

    override fun createUser(user: UserRequestCreateDto): UserEntity = (
            requireNotNull(
                dsl.insertInto(USERS)
                    .set(USERS.ID, UUID.randomUUID())
                    .set(USERS.FIRSTNAME, user.firstname)
                    .set(USERS.LASTNAME, user.lastname)
                    .set(USERS.EMAIL, user.email)
                    .returning()
                    .fetchOne()
            ) { "Error create user" }.toResponse()
            )


    override fun findAllUser(): List<UserEntity> = (
            dsl.selectFrom(USERS)
                .fetch()
                .map { user -> user.toResponse() })

    override fun findUserById(id: UUID): UserEntity = (dsl.selectFrom(USERS)
        .where(USERS.ID.eq(id))
        .fetchOne()
        ?.toResponse()
        ?: throw UserNotFoundException(id))

    override fun updateUserById(id: UUID, user: UserUpdateDto): UserEntity {
        val query = dsl.update(USERS).set(USERS.UPDATED_AT, LocalDateTime.now())


        user.firstName?.let {
            query.set(USERS.FIRSTNAME, it)
        }

        user.email?.let {
            query.set(USERS.EMAIL, it)
        }

        user.lastName?.let {
            query.set(USERS.LASTNAME, it)
        }

        return query
            .where(USERS.ID.eq(id))
            .returning()
            .fetchOneInto(UserEntity::class.java)
            ?: throw UserNotFoundException(id)
    }

    private fun UsersRecord.toResponse(): UserEntity = (
            UserEntity(
                id = id,
                email = email,
                firstName = firstname,
                lastName = lastname,
                createAt = createdAt,
                updateAt = updatedAt,
            ))
}