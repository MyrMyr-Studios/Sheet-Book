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


fun Route.sheetRouting() {
    route("/sheets") {
        listSheets()
        getSheetById()
        getSheetByUserId()
        getSheetByUserIdAndName()
        addSheet()
        deleteSheetById()
        updateSheetById()
    }
}

fun Route.listSheets() {
    get {
        val session = call.sessions.get<UserSession>()
        if (session == null)
            return@get call.respond(HttpStatusCode.Unauthorized)
        val sheets = dao.findSheetByOwnerId(session.id)//sheetStorage.filter { it.ownerId == session.id }
        call.respond(HttpStatusCode.OK, Json.encodeToString(sheets))
    }
}

// Devolve a ficha com o ID especificado
fun Route.getSheetById() {
    /*get("{id?}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            "ID faltando\n",
            status = HttpStatusCode.BadRequest
        )
        val sheet = sheetStorage.find { it.sheetId == id.toInt() } ?: return@get call.respondText(
            "Sem ficha com id $id\n",
            status = HttpStatusCode.NotFound
        )
        call.respond(sheet)
    }*/
    get {
        val session = call.sessions.get<UserSession>()
        if (session == null)
            return@get call.respond(HttpStatusCode.Unauthorized)
        val id = URLDecoder.decode(call.parameters["id"], "UTF-8") ?: return@get call.respond(HttpStatusCode.BadRequest)
        val sheet = dao.findSheetById(id)//sheetStorage.find { it.sheetId == id.toInt() } ?:
        if(sheet == null) return@get call.respond(HttpStatusCode.NotFound)
        call.respond(HttpStatusCode.OK, sheet)
    }
}

// Devolve todas as fichas do usuario especificado
/*fun Route.getSheetByUserId() {
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
}*/

// Devolve todas as fichas com o nome especificdo do usuario especificado
/*fun Route.getSheetByUserIdAndName() {
    get("userSheets/{userId?}/{sheetName?}") {
        val userId = call.parameters["userId"] ?: return@get call.respondText(
            "ID do usuario faltando\n",
            status = HttpStatusCode.BadRequest
        )
        val sheetName = URLDecoder.decode(call.parameters["sheetName"], "UTF-8") ?: return@get call.respondText(
            "Nome do personagem faltando\n",
            status = HttpStatusCode.BadRequest
        )
        if (userStorage.find { it.userId == userId.toInt() } == null) return@get call.respondText(
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
}*/

// Adiciona a ficha recebida
fun Route.addSheet() {
    post {
        /*val sheet = call.receive<Sheet>()
        if (userStorage.find { it.userId == sheet.ownerId } == null) return@post call.respondText(
            "Usuario ${sheet.ownerId} nao existe\n",
            status = HttpStatusCode.NotFound
        )
        var maxId = -1
        sheetStorage.forEach {
            maxId = if (it.sheetId > maxId) it.sheetId else maxId
        }
        sheet.sheetId = if (sheetStorage.isEmpty()) 0 else maxId + 1
        sheetStorage.add(sheet)
        call.respondText("Ficha adicionada com sucesso!\n", status = HttpStatusCode.Created)*/
        val session = call.sessions.get<UserSession>()
        if (session == null)
            return@post call.respond(HttpStatusCode.Unauthorized)
        val sheet = call.receive<Sheet>()
        if(dao.findUserById(sheet.ownerId) == null) return@post respond(HttpStatusCode.NotFound)
        if(session.id != sheet.ownerId) return@post respond(HttpStatusCode.Unauthorized)
        if(dao.addNewSheet(sheet) == null) return@post respond(HttpStatusCode.InternalServerError)
        respond(HttpStatusCode.Created)
    }
}

// Apaga a ficha especificada
fun Route.deleteSheetById() {
    /*delete("{id?}") {
        val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
        if (sheetStorage.find { it.sheetId == id.toInt() } == null) return@delete call.respondText(
            "Ficha $id nao existe\n",
            status = HttpStatusCode.NotFound
        )
        campaignStorage.forEach { (_, _, _, sheetList): Campaign -> sheetList.removeIf { it == id.toInt() } }
        sheetStorage.removeIf { it.sheetId == id.toInt() }
        call.respondText("Ficha $id removida com sucesso\n", status = HttpStatusCode.Accepted)
    }*/
    delete {
        val session = call.sessions.get<UserSession>()
        if (session == null)
            return@delete call.respond(HttpStatusCode.Unauthorized)
        val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
        val sheet = dao.findSheetById(id)
        if(sheet == null) return@delete call.respond(HttpStatusCode.NotFound)
        if(sheet.ownerId != session.id) return@delete call.respond(HttpStatusCode.Unauthorized)
        if(dao.deleteSheet(id) == false) return@delete call.respond(HttpStatusCode.InternalServerError)
        call.respond(HttpStatusCode.Accepted)
    }
}

// Atualiza a ficha especificada com as informaçoes mandadas
fun Route.updateSheetById() {
    /*post("{sheetId?}") {
        val sheetId = call.parameters["sheetId"] ?: return@post call.respond(HttpStatusCode.BadRequest)
        val originalSheet = sheetStorage.find { it.sheetId == sheetId.toInt() }
        if (originalSheet == null) return@post call.respondText(
            "Ficha $sheetId nao existe\n",
            status = HttpStatusCode.NotFound
        )
        val updatedSheet = call.receive<Sheet>()
        if (updatedSheet.sheetId != originalSheet.sheetId) return@post call.respondText(
            "sheetId's sao diferentes\n",
            status = HttpStatusCode.Conflict
        )
        if (updatedSheet.ownerId != originalSheet.ownerId) return@post call.respondText(
            "ownerId's sao diiferentes\n",
            status = HttpStatusCode.Conflict
        )
        sheetStorage.forEachIndexed { index, sheet ->
            sheet.takeIf { it.sheetId == sheetId.toInt() }.let {
                sheetStorage[index] = updatedSheet
            }
        }
        call.respondText("Ficha $sheetId atualizada com sucesso!\n", status = HttpStatusCode.OK)
    }*/
    post {
        val session = call.sessions.get<UserSession>()
        if (session == null)
            return@post call.respond(HttpStatusCode.Unauthorized)
        val sheetId = call.parameters["sheetId"] ?: return@post call.respond(HttpStatusCode.BadRequest)
        val originalSheet = dao.findSheetById(sheetId)
        if(originalSheet == null) return@post call.respond(HttpStatusCode.NotFound)
        val updatedSheet = call.receive<Sheet>()
        if(updatedSheet.sheetId != sheetId) return@post call.respond(HttpStatusCode.BadRequest)
        if(updatedSheet.ownerId != originalSheet.ownerId || originalSheet.ownerId != session.id)
            return@post call.respond(HttpStatusCode.Unauthorized)
        if(dao.editSheet(updatedSheet) == false) return@post call.respond(HttpStatusCode.InternalServerError)
        call.respond(HttpStatusCode.OK)
    }
}