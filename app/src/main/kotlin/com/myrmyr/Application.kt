package com.myrmyr

import com.myrmyr.dao.DatabaseSingleton
import io.ktor.server.plugins.cors.routing.*
import com.myrmyr.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.http.HttpHeaders
import io.ktor.server.sessions.*

data class UserSession(val id: Int)

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
        allowCredentials = true 
    }
    install(Sessions) {
        cookie<UserSession>("user_session", SessionStorageMemory()){
            cookie.secure = true
        }
    }
    DatabaseSingleton.init()
    configureSerialization()
    configureRouting()
}
