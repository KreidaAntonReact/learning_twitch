package ru.twitch.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.springframework.session.web.http.CookieSerializer
import org.springframework.session.web.http.DefaultCookieSerializer

@Configuration
@EnableRedisHttpSession(
    maxInactiveIntervalInSeconds = 86400,
    redisNamespace = "redis"
)
@EnableConfigurationProperties(ConfigPropertiesCookie::class)
class SessionConfig (
    val propertiesCookie: ConfigPropertiesCookie
) {

    @Bean
    fun cookieSerialize(): CookieSerializer {
        val serializer = DefaultCookieSerializer()

        serializer.setCookieName(propertiesCookie.name);
        serializer.setCookiePath(propertiesCookie.path);
        serializer.setCookieMaxAge(propertiesCookie.maxAge);
        serializer.setUseSecureCookie(propertiesCookie.secure);
        serializer.setUseHttpOnlyCookie(propertiesCookie.httpOnly);
        serializer.setSameSite(propertiesCookie.sameSite.value)

        return serializer
    }
}