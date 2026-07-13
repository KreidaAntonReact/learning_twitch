package ru.twitch.users.reposotories

import org.jooq.DSLContext
import com.example.demo.jooq.Tables.USERS
import com.example.demo.jooq.tables.records.UsersRecord
import org.springframework.stereotype.Repository
import ru.twitch.common.utils.CreateException
import ru.twitch.common.utils.NotFoundException
import ru.twitch.users.domian.UserEntity
import ru.twitch.users.dto.UserRequestCreateDto
import java.util.UUID

@Repository
class JooqUserRepository(
    private val dsl: DSLContext
) : UserRepository {

    override fun createUser(user: UserRequestCreateDto): UserEntity = (
            dsl.insertInto(USERS)
                .set(USERS.ID, UUID.randomUUID())
                .set(USERS.USERNAME, user.username)
                .set(USERS.PASSWORD, user.password)
                .set(USERS.EMAIL, user.email)
                .set(USERS.AVATAR, user.avatar)
                .set(USERS.BIO, user.bio)
                .set(USERS.DISPLAY_NAME, user.displayName)
                .returning()
                .fetchOne()?.toResponse() ?: throw CreateException("Create user error")
            )


    override fun findAllUser(): List<UserEntity> = (
            dsl.selectFrom(USERS)
                .fetch()
                .map { user -> user.toResponse() })

    override fun findUserById(id: UUID): UserEntity = (
            dsl.selectFrom(USERS)
                .where(USERS.ID.eq(id))
                .fetchOne()
                ?.toResponse()
                ?: throw NotFoundException("User not found by $id"))

    override fun findUserByEmail(email: String): UserEntity? = (
            dsl.selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOne()
                ?.toResponse()
            )

    override fun findUserByUsername(username: String): UserEntity? = (
            dsl.selectFrom(USERS)
                .where(USERS.USERNAME.eq(username))
                .fetchOne()
                ?.toResponse()
            )

    private fun UsersRecord.toResponse(): UserEntity = (
            UserEntity(
                id = id,
                email = email,
                password = password,
                username = username,
                avatar = avatar,
                bio = bio,
                displayName = displayName,
                createAt = createdAt,
                updateAt = updatedAt,
            ))
}