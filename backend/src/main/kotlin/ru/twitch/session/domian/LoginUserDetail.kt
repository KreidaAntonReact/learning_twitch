package ru.twitch.session.domian

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.twitch.users.domian.UserEntity

data class LoginUserDetail(
    val usernameValue: String = "",
    val passwordValue: String,
    val roles: Collection<GrantedAuthority>
) : UserDetails {
    override fun getUsername(): String  = this.usernameValue

    override fun getPassword(): String = this.passwordValue

    override fun getAuthorities(): Collection<GrantedAuthority> = mutableListOf()
}