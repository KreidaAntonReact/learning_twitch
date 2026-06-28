package ru.twitch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TwitchApplication

fun main(args: Array<String>) {
    runApplication<ru.twitch.TwitchApplication>(*args)
}
