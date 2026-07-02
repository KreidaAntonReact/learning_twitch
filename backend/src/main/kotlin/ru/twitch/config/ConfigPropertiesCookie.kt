package ru.twitch.config

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = ConfigPropertiesCookie.PREFIX)
class ConfigPropertiesCookie (
    /** Add option name for cookie in project */
    val name: String = "sid",

    /** Add option path for cookie in project */
    val path: String = "/",

    /** Add option maxAge for cookie in project */
    val maxAge: Int = 3600,

    /** Add option secure for cookie in project */
    val secure: Boolean = false,

    /** Add option httpOnly for cookie in project */
    val httpOnly: Boolean = false,

    /** Add option sameSite for cookie in project */
    val sameSite: SAME_SITE_ENUM = SAME_SITE_ENUM.LAX,
) {
    companion object {
        const val PREFIX = "twitch.cookie"
    }

    enum class SAME_SITE_ENUM(val value: String) {
        LAX("Lax"),
        STRICT("Strict"),
        NONE("None")
    }
}