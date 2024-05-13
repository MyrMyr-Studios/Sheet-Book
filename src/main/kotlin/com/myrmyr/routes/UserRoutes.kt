package com.myrmyr.routes

import com.myrmyr.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.net.URLDecoder
import io.ktor.server.sessions.*
import com.myrmyr.UserSession

fun Route.userRouting() {
    route("/users") {
        listAllUsers()
    }
    route("/login") {
        login()
        signUp()
        logout()
    }
}

// Devolve todos os usuarios salvos
fun Route.listAllUsers() {
    get {
        if (userStorage.isNotEmpty()) {
            call.respond(userStorage)
        } else {
            call.respondText("Sem usuarios\n", status = HttpStatusCode.OK)
        }
    }
}

fun Route.login() {
    get {
        val email = URLDecoder.decode(call.parameters["email"], "UTF-8") ?: return@get call.respond(HttpStatusCode.BadRequest)
        val password = URLDecoder.decode(call.parameters["password"], "UTF-8") ?: return@get call.respond(HttpStatusCode.BadRequest)
        userStorage.find { it.email == email }?.let {
            if (it.password == password) {
                call.sessions.set(UserSession(id = it.userId))
                call.respondText("Login efetuado com sucesso\n", status = HttpStatusCode.OK)
            } else call.respondText("Senha incorreta\n", status = HttpStatusCode.Unauthorized)
        } ?: call.respondText("Usuario nao encontrado\n", status = HttpStatusCode.NotFound)
    }
}

fun Route.signUp() {
    post {
        val user = call.receive<User>()
        var maxId = -1
        userStorage.forEach {
            maxId = if (it.userId > maxId) it.userId else maxId
        }
        user.userId = if (userStorage.isEmpty()) 0 else maxId + 1
        if (userStorage.find { it.email == user.email } != null) return@post call.respondText(
            "Email ja utilizado\n",
            status = HttpStatusCode.Conflict
        )
        userStorage.add(user)
        call.sessions.set(UserSession(id = user.userId))
        call.respondText("Usuario adicionado com sucesso!\n", status = HttpStatusCode.Created)
    }
}

fun Route.logout() {
    get {
        call.sessions.clear<UserSession>()
        call.respondText("Logout efetuado com sucesso\n", status = HttpStatusCode.OK)
    }
}