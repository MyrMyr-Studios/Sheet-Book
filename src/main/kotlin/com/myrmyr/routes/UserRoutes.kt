package com.myrmyr.routes

import com.myrmyr.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRouting() {
    route("/users") {
        listAllUsers()
        getUserById()
        addUser()
        deleteUserById()
    }
}

fun Route.listAllUsers() {
    get {
        if (userStorage.isNotEmpty()) {
            call.respond(userStorage)
        } else {
            call.respondText("Sem usuarios\n", status = HttpStatusCode.OK)
        }
    }
}

fun Route.getUserById() {
    get("{id?}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            "ID faltando\n",
            status = HttpStatusCode.BadRequest
        )
        val user = userStorage.find { it.id == id.toInt() } ?: return@get call.respondText(
            "Sem usuario com o ID $id\n",
            status = HttpStatusCode.NotFound
        )
        call.respond(user)
    }
}

fun Route.addUser() {
    post {
        val user = call.receive<User>()
        if (user in userStorage) return@post call.respondText(
            "Usuario ja existe\n",
            status = HttpStatusCode.Conflict
        )
        userStorage.add(user)
        call.respondText("Usuario adicionado com sucesso!\n", status = HttpStatusCode.Created)
    }
}

fun Route.deleteUserById() {
    delete("{id?}") {
        val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
        sheetStorage.removeIf { it.ownerId == id.toInt() }
        if (userStorage.removeIf { it.id == id.toInt() }) {
            call.respondText("Usuario removido corretamente\n", status = HttpStatusCode.Accepted)
        } else {
            call.respondText("Nao achado\n", status = HttpStatusCode.NotFound)
        }
    }
}