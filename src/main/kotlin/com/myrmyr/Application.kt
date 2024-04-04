package com.myrmyr

import com.myrmyr.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
// import io.ktor.server.plugins.cors.routing.*
// import io.ktor.features.CORS

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    // install(CORS)
}