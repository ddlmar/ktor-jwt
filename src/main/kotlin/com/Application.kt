package com

import com.plugins.configureSerialization
import com.repository.UserRepository
import com.routing.configureRouting
import com.service.UserService
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val userRepository = UserRepository()
    val userService = UserService(userRepository)

    configureSerialization()
    configureRouting(userService)
}
