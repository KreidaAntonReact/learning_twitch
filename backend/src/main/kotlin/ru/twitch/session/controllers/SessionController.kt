package ru.twitch.session.controllers

import jakarta.servlet.http.HttpServletRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.stereotype.Controller
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import ru.twitch.session.domian.LoginUserDetail
import ru.twitch.session.dto.LoginInput
import ru.twitch.session.services.SessionService
import ru.twitch.users.domian.UserEntity


@Controller
class SessionController (
    private val sessionService: SessionService
) {

    @MutationMapping
    fun login(@Argument input: LoginInput): UserEntity {
        val user = sessionService.login(input)

        val userDetail = LoginUserDetail(
            usernameValue = user.username,
            passwordValue = user.password,
            roles = emptyList()
        )

        val auth: Authentication = UsernamePasswordAuthenticationToken(
          userDetail,
            null,
            userDetail.authorities,
        )

        val context: SecurityContext = SecurityContextHolder.createEmptyContext();
        context.authentication = auth
        SecurityContextHolder.setContext(context)

        val request = this.currentRequest();


        request.session.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            context
        )

        return user;
    }

    private fun currentRequest(): HttpServletRequest {
        val attributes = RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes
        return attributes.request
    }
}