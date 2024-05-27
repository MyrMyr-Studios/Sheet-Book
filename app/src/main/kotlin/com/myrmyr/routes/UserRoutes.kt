package com.myrmyr.routes

import com.myrmyr.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.net.URLDecoder
import io.ktor.server.sessions.*
import com.myrmyr.UserSession
import com.myrmyr.dao.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString


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
        val session = call.sessions.get<UserSession>()
        if (session == null) {
            call.respondText("Nao autorizado\n", status = HttpStatusCode.Unauthorized)
            return@get
        }
        val userList = dao.allUsers()
        if (userList.isEmpty()) {
            call.respondText("Sem usuarios\n", status = HttpStatusCode.NotFound)
        } else {
            call.respond(HttpStatusCode.OK, Json.encodeToString(userList))
        }
    }
}

fun Route.login() {
    get {
        val email = URLDecoder.decode(call.parameters["email"], "UTF-8") ?: return@get call.respond(HttpStatusCode.BadRequest)
        val password = URLDecoder.decode(call.parameters["password"], "UTF-8") ?: return@get call.respond(HttpStatusCode.BadRequest)
        val user = dao.findUserByEmail(email)
        if (user == null) call.respondText("Usuario nao encontrado\n", status = HttpStatusCode.NotFound)
        if (user!!.password == password) {
            call.sessions.set(UserSession(id = user.userId))
            call.respondText("Login efetuado com sucesso\n", status = HttpStatusCode.OK)
        } else call.respondText("Senha incorreta\n", status = HttpStatusCode.Unauthorized)
    }
}

fun Route.signUp() {
    post {
        val user = call.receive<User>()
        if (dao.findUserByEmail(user.email) != null) return@post call.respondText(
            "Email ja utilizado\n",
            status = HttpStatusCode.Conflict
        )
        if (dao.addNewUser(user.name, user.email, user.password) != null) {
            call.sessions.set(UserSession(id = user.userId))
            call.respondText("Usuario adicionado com sucesso!\n", status = HttpStatusCode.Created)
        }
    }
}

fun Route.logout() {
    get {
        call.sessions.clear<UserSession>()
        call.respondText("Logout efetuado com sucesso\n", status = HttpStatusCode.OK)
    }
}