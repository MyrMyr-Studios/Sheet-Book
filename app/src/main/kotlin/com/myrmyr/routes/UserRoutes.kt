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
    route("/user") {
        getUsername()
    }
    route("/login") {
        login()
        signUp()
    }
    route("/logout") {
        logout()
    }
}

fun Route.getUsername() {
    get {
        val session = call.sessions.get<UserSession>()
        if (session == null) return@get call.respond(HttpStatusCode.BadRequest)

        val user = dao.findUserById(session.id)
        call.respond(HttpStatusCode.OK, Json.encodeToString(user!!.name))
    }
}

fun Route.login() {
    get {
        val email = URLDecoder.decode(call.parameters["email"], "UTF-8") ?: return@get call.respond(HttpStatusCode.BadRequest)
        val password = URLDecoder.decode(call.parameters["password"], "UTF-8") ?: return@get call.respond(HttpStatusCode.BadRequest)
        val user = dao.findUserByEmail(email)
        if (user == null) return@get call.respond(HttpStatusCode.NotFound)

        if (user!!.password == password) {
            call.sessions.set(UserSession(id = user.userId))
            call.respond(HttpStatusCode.OK)
        } else call.respond(HttpStatusCode.Unauthorized)
    }
}

fun Route.signUp() {
    post {
        val user = call.receive<User>()
        if (dao.findUserByEmail(user.email) != null) return@post call.respond(HttpStatusCode.Conflict)
        if (dao.addNewUser(user.name, user.email, user.password) != null) {
            val db_user = dao.findUserByEmail(user.email)
            if (db_user == null) return@post call.respond(HttpStatusCode.InternalServerError)
            call.sessions.set(UserSession(id = db_user.userId))
            call.respond(HttpStatusCode.Created)
        }
    }
}

fun Route.logout() {
    get {
        call.sessions.clear<UserSession>()
        call.respond(HttpStatusCode.OK)
    }
}