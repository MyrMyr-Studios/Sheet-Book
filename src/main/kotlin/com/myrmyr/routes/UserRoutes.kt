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
        getUserByEmail()
        deleteUserById()
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

// Devolve o usuario com o id especificado
fun Route.getUserById() {
    get("id={id?}") {
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

// Devolve o usuario com o email especificado
fun Route.getUserByEmail() {
    get("email={email?}") {
        val email = call.parameters["email"] ?: return@get call.respondText(
            "E-mail faltando\n",
            status = HttpStatusCode.BadRequest
        )
        val user = userStorage.find { it.email == email } ?: return@get call.respondText(
            "Sem usuario com o e-mail $email\n",
            status = HttpStatusCode.NotFound
        )
        call.respond(user)
    }
}

// Adiciona o usuario
fun Route.addUser() {
    post {
        val user = call.receive<User>()
        var maxId = -1
        userStorage.forEach {
            maxId = if (it.id > maxId) it.id else maxId
        }
        user.id = if (userStorage.isEmpty()) 0 else maxId + 1
        if (userStorage.find { it.email == user.email } != null) return@post call.respondText(
            "Email ja utilizado\n",
            status = HttpStatusCode.Conflict
        )
        userStorage.add(user)
        call.respond(user)
        call.respondText("Usuario adicionado com sucesso!\n", status = HttpStatusCode.Created)
    }
}

// Deleta o usuario com o id especificado
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