package com.myrmyr.routes

import com.myrmyr.models.*
//import com.typesafe.config.ConfigException.Null
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.sheetRouting() {
    route("/sheets") {
        listAllSheets()
        getSheetById()
        getSheetByUserId()
        getSheetByUserIdAndName()
        addSheet()
        deleteSheetById()
    }
}

// Devolve todas as fichas armazenadas
fun Route.listAllSheets() {
    get {
        if (sheetStorage.isNotEmpty()) {
            call.respond(sheetStorage)
        } else {
            call.respondText("Sem fichas\n", status = HttpStatusCode.OK)
        }
    }
}

// Devolve a ficha com o ID especificado
fun Route.getSheetById() {
    get("{id?}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            "ID faltando\n",
            status = HttpStatusCode.BadRequest
        )
        val sheet = sheetStorage.find { it.sheetId == id.toInt() } ?: return@get call.respondText(
            "Sem ficha com id $id\n",
            status = HttpStatusCode.NotFound
        )
        call.respond(sheet)
    }
}

// Devolve todas as fichas do usuario especificado
fun Route.getSheetByUserId() {
    get("userSheets/{userId?}") {
        val userId = call.parameters["userId"] ?: return@get call.respondText(
            "ID do usuario faltando\n",
            status = HttpStatusCode.BadRequest
        )
        val sheets = sheetStorage.filter { it.ownerId == userId.toInt() }
        if (sheets.isEmpty()) return@get call.respondText(
            "Usuario $userId nao possui fichas\n",
            status = HttpStatusCode.NotFound
        )
        call.respond(sheets)
    }
}

// Devolve todas as fichas com o nome especificdo do usuario especificado
fun Route.getSheetByUserIdAndName() {
    get("userSheets/{userId?}/{sheetName?}") {
        val userId = call.parameters["userId"] ?: return@get call.respondText(
            "ID do usuario faltando\n",
            status = HttpStatusCode.BadRequest
        )
        val sheetName = call.parameters["sheetName"] ?: return@get call.respondText(
            "Nome do personagem faltando\n",
            status = HttpStatusCode.BadRequest
        )
        if (userStorage.find { it.id == userId.toInt() } == null) return@get call.respondText(
            "Usuario $userId nao existe\n",
            status = HttpStatusCode.NotFound
        )
        val sheets = sheetStorage.filter { it.ownerId == userId.toInt() && it.name == sheetName }
        if (sheets.isEmpty()) return@get call.respondText(
            "Usuario $userId nao possui ficha de $sheetName\n",
            status = HttpStatusCode.NotFound
        )
        call.respond(sheets)
    }
}

// Adiciona a ficha recebida
fun Route.addSheet() {
    post {
        val sheet = call.receive<Sheet>()
        if (userStorage.find { it.id == sheet.ownerId } == null) return@post call.respondText(
            "Usuario ${sheet.ownerId} nao existe\n",
            status = HttpStatusCode.NotFound
        )
        var maxId = -1
        sheetStorage.forEach {
            maxId = if (it.sheetId > maxId) it.sheetId else maxId
        }
        sheet.sheetId = if (sheetStorage.isEmpty()) 0 else maxId + 1
        sheetStorage.add(sheet)
        call.respondText("Ficha adicionada com sucesso!\n", status = HttpStatusCode.Created)
    }
}

// Apaga a ficha especificada
fun Route.deleteSheetById() {
    delete("{id?}") {
        val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
        if (sheetStorage.find { it.sheetId == id.toInt() } == null) return@delete call.respondText(
            "Ficha $id nao existe\n",
            status = HttpStatusCode.NotFound
        )
        campaignStorage.forEach { (_, _, _, sheetList): Campaign -> sheetList.removeIf { it == id.toInt() } }
        sheetStorage.removeIf { it.sheetId == id.toInt() }
        /*if (sheetStorage.removeIf { it.sheetId == id.toInt() }) {
            call.respondText("Ficha removida corretamente\n", status = HttpStatusCode.Accepted)
        } else {
            call.respondText("Ficha $id nao foi encontrada\n", status = HttpStatusCode.NotFound)
        }*/
    }
}