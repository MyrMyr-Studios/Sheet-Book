package com.myrmyr.routes

import com.myrmyr.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.net.URLDecoder

fun Route.userRouting() {
    route("/users") {
        listAllUsers()
        getUserById()
        addUser()
        getUserByEmail()
        deleteUserById()
        checkPassword()
    }
}

// Gambiarrada pra nao mandar a senha junto
@Serializable
data class UserWithoutPassword(
    val id: Int,
    val name: String,
    val email: String
)

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
        val uwp = UserWithoutPassword(user.id, user.name, user.email)
        call.respond(uwp)
    }
}

// Devolve o usuario com o email especificado
fun Route.getUserByEmail() {
    get("email={email?}") {
        val email = URLDecoder.decode(call.parameters["email"], "UTF-8") ?: return@get call.respondText(
            "E-mail faltando\n",
            status = HttpStatusCode.BadRequest
        )
        val user = userStorage.find { it.email == email } ?: return@get call.respondText(
            "Sem usuario com o e-mail $email\n",
            status = HttpStatusCode.NotFound
        )
        val uwp = UserWithoutPassword(user.id, user.name, user.email)
        call.respond(uwp)
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
        if (userStorage.find { it.id == id.toInt() } == null) return@delete call.respondText(
            "Usuario $id nao existe\n",
            status = HttpStatusCode.NotFound
        )
        campaignStorage.forEach { (_, _, userList): Campaign -> userList.removeIf { it == id.toInt() } }
        sheetStorage.forEach { (ownerId, targetId): Sheet ->
            if (ownerId == id.toInt()) {
                campaignStorage.forEach { it: Campaign -> it.sheetList.removeIf { it == targetId } }
            }
        }
        sheetStorage.removeIf { it.ownerId == id.toInt() }
        userStorage.removeIf { it.id == id.toInt() }
    }
}

// Devolve True se a tentativa de senha corresponde a senha do usuario, falso c.c.
fun Route.checkPassword() {
    get("id={id?}/password={password?}") {
        val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
        if (userStorage.find { it.id == id.toInt() } == null) return@get call.respondText(
            "Usuario $id nao existe\n",
            status = HttpStatusCode.NotFound
        )
        val user = userStorage.find { it.id == id.toInt() }
        val password = URLDecoder.decode(call.parameters["password"], "UTF-8") ?: return@get call.respond(HttpStatusCode.BadRequest)
        if (password == user?.password) {
            call.respond(true)
        } else call.respond(false)
    }
}