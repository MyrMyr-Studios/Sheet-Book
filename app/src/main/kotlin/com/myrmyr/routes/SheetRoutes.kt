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
        // Listar fichas
        get {
            val session = call.sessions.get<UserSession>()
            if (session == null) return@get call.respond(HttpStatusCode.Unauthorized)
            val sheets = dao.findSheetByOwnerId(session.id)
            call.respond(HttpStatusCode.OK, Json.encodeToString(sheets))
        }

        // Retornar ficha por id
        get("{id?}") {
            val id = (URLDecoder.decode(call.parameters["id"], "UTF-8")).toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            val sheet = dao.findSheetById(id)
            if(sheet == null) return@get call.respond(HttpStatusCode.NotFound)
            call.respond(HttpStatusCode.OK, sheet)
        }

        // Atualizar ficha
        post {
            val session = call.sessions.get<UserSession>()
            if (session == null) return@post call.respond(HttpStatusCode.Unauthorized)
            val sheet = call.receive<Sheet>()
            sheet.ownerId = session.id
            if(sheet.sheetId == -1) {
                val newSheet = dao.addNewSheet(sheet)
                if(newSheet == null) return@post call.respond(HttpStatusCode.InternalServerError)
                return@post call.respond(HttpStatusCode.Created, Json.encodeToString(newSheet))
            }
            if(dao.editSheet(sheet) == false) return@post call.respond(HttpStatusCode.InternalServerError)
            call.respond(HttpStatusCode.OK)
        }
    }

    route("/sheets/delete") {
        // Deletar ficha por id
        get("{id?}") {
            val session = call.sessions.get<UserSession>()
            if (session == null) return@get call.respond(HttpStatusCode.Unauthorized)
            val id = (URLDecoder.decode(call.parameters["id"], "UTF-8")).toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            val sheet = dao.findSheetById(id)
            if(sheet == null) return@get call.respond(HttpStatusCode.NotFound)
            if(dao.deleteSheet(id) == false) return@get call.respond(HttpStatusCode.InternalServerError)
            call.respond(HttpStatusCode.OK)
        }
    }
        
}