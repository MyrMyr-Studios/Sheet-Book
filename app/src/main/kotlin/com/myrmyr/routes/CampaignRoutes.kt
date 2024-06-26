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
import kotlinx.serialization.Serializable

@Serializable data class CampaignInfo(val users: List<Map<String, String>>, val sheets: List<Sheet>, val name: String)
@Serializable data class UserPayload(val id: Int, val email: String)

fun Route.campaignRouting() {
    route("/campaigns") {
        // Listar campanhas
        get {
            val session = call.sessions.get<UserSession>()
            if (session == null)
                return@get call.respond(HttpStatusCode.Unauthorized)
            val campaigns = dao.getUserCampaigns(session.id)
            call.respond(HttpStatusCode.OK, Json.encodeToString(campaigns))
        }
        
        // Retornar campanha por id
        get("{id?}") {
            val id = (URLDecoder.decode(call.parameters["id"], "UTF-8")).toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            val campaign = dao.findCampaignById(id)
            if(campaign == null) return@get call.respond(HttpStatusCode.NotFound)
            call.respond(HttpStatusCode.OK, campaign)
        }

        // Adicionar campanha
        post {
            val session = call.sessions.get<UserSession>()
            if (session == null) return@post call.respond(HttpStatusCode.Unauthorized)
            val campaignName = call.receive<Campaign>().name
            val newCampaign = dao.addNewCampaign(campaignName)
            if(newCampaign == null) return@post call.respond(HttpStatusCode.InternalServerError)
            dao.addUserToCampaign(session.id, newCampaign.campaignId)
            return@post call.respond(HttpStatusCode.Created, Json.encodeToString(newCampaign))
        }
    }

    route("/campaign/users") {
        // Adicionar usuário a uma campanha por email
        post {
            val payload = call.receive<UserPayload>()
            val user = dao.findUserByEmail(payload.email)
            if(user == null) return@post call.respond(HttpStatusCode.NotFound)
            val campaign = dao.findCampaignById(payload.id)
            if(campaign == null) return@post call.respond(HttpStatusCode.NotFound)
            if(user.userId in campaign.userList) return@post call.respond(HttpStatusCode.NotModified)
            if(dao.addUserToCampaign(user.userId, campaign.campaignId) == false) return@post call.respond(HttpStatusCode.InternalServerError)
            call.respond(HttpStatusCode.OK) // WIP
        }

        // Remover usuário de uma campanha por email
        get("{id? , email?}") {
            val id = (URLDecoder.decode(call.parameters["id"], "UTF-8")).toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            val email = (URLDecoder.decode(call.parameters["email"], "UTF-8"))
            val user = dao.findUserByEmail(email)
            if(user == null) return@get call.respond(HttpStatusCode.NotFound)
            if(dao.removeUserFromCampaign(user.userId, id) == false) return@get call.respond(HttpStatusCode.InternalServerError)
            call.respond(HttpStatusCode.OK) // WIP
        }
    }

    route("/campaign/sheets") {
        // Adicionar ficha a uma campanha
        post {
            val id = (URLDecoder.decode(call.parameters["id"], "UTF-8")).toIntOrNull() ?: return@post call.respond(HttpStatusCode.BadRequest)
            val sheetId = (URLDecoder.decode(call.parameters["sheetId"], "UTF-8")).toIntOrNull() ?: return@post call.respond(HttpStatusCode.BadRequest)
            val sheet = dao.findSheetById(sheetId)
            if(sheet == null) return@post call.respond(HttpStatusCode.NotFound)
            if(dao.addSheetToCampaign(sheetId, id) == false) return@post call.respond(HttpStatusCode.InternalServerError)
            call.respond(HttpStatusCode.OK) // WIP
        }

        // Remover ficha de uma campanha WIP
        // get("{id? , sheetId?}") {
        //     val id = (URLDecoder.decode(call.parameters["id"], "UTF-8")).toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
        //     val sheetId = (URLDecoder.decode(call.parameters["sheetId"], "UTF-8")).toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
        //     val sheet = dao.findSheetById(sheetId)
        //     if(sheet == null) return@get call.respond(HttpStatusCode.NotFound)
        //     if(dao.removeSheetFromCampaign(sheetId, id) == false) return@get call.respond(HttpStatusCode.InternalServerError)
        //     call.respond(HttpStatusCode.OK) // WIP
        // }
    }

    route("/campaign/delete") {
        // Deletar campanha
        get("{id?}") {
            val id = (URLDecoder.decode(call.parameters["id"], "UTF-8")).toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            val campaign = dao.findCampaignById(id)
            if(campaign == null) return@get call.respond(HttpStatusCode.NotFound)
            if(dao.deleteCampaign(id) == false) return@get call.respond(HttpStatusCode.InternalServerError)
            call.respond(HttpStatusCode.OK)
        }
    }
    route("/campaign/info") {
        // Retorna lista de usuarios (nome, email) e lista de sheets
        get("{id?}") {
            val id = (URLDecoder.decode(call.parameters["id"], "UTF-8")).toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            val campaign = dao.findCampaignById(id)
            if(campaign == null) return@get call.respond(HttpStatusCode.NotFound)
            val users: MutableList<Map<String, String>> = mutableListOf<Map<String, String>>()
            for(userId in campaign.userList) {
                val user = dao.findUserById(userId)
                if(user == null) return@get call.respond(HttpStatusCode.NotFound)
                users.add(mapOf("name" to user.name, "email" to user.email, "id" to user.userId.toString()))
            }
            val sheets: MutableList<Sheet> = mutableListOf<Sheet>()
            for(sheetId in campaign.sheetList) {
                val sheet = dao.findSheetById(sheetId)
                if(sheet == null) return@get call.respond(HttpStatusCode.NotFound)
                sheets.add(sheet)
            }
            val campaignInfo = CampaignInfo(users, sheets, campaign.name)
            call.respond(HttpStatusCode.OK, Json.encodeToString(campaignInfo))
        }
    }
}