package com.myrmyr

import io.ktor.server.plugins.cors.routing.*
import com.myrmyr.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpHeaders

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeader("X-Requested-With")
        allowHeader(HttpHeaders.Origin)
    }
    configureSerialization()
    configureRouting()
}
