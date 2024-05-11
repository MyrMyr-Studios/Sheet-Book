package com.myrmyr.routes

import com.myrmyr.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

fun Route.campaignRouting() {
    route("/campaigns") {
        listAllCampaigns()
        getCampaignById()
        getCampaignsByName()
        getUserCampaigns()
        addCampaign()
        addUserIdToCampaign()
        addSheetIdToCampaign()
    }
}

// Devolve todas as campanhas salvas
fun Route.listAllCampaigns() {
    get {
        if (campaignStorage.isNotEmpty()) {
            call.respond(campaignStorage)
        } else {
            call.respondText(
                "Sem campanhas\n",
                status = HttpStatusCode.OK
            )
        }
    }
}

// Devolve a campanha com o id especificado
fun Route.getCampaignById() {
    get("campaignId={id?}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            "ID faltando\n",
            status = HttpStatusCode.BadRequest
        )
        val campaign = campaignStorage.find { it.campaignId == id.toInt() } ?: return@get call.respondText(
            "Sem campanha com id $id\n",
            status = HttpStatusCode.NotFound
        )
        call.respond(campaign)
    }
}

// Devolve todas as campanhas com o nome especificado
fun Route.getCampaignsByName() {
    get("name={name?}") {
        val name = call.parameters["name"] ?: return@get call.respondText(
            "Nome faltando\n",
            status = HttpStatusCode.BadRequest
        )
        val campaigns = campaignStorage.filter { it.name == name }
        if (campaigns.isEmpty()) return@get call.respondText(
            "Nenhuma campanha com nome $name\n",
            status = HttpStatusCode.NotFound
        )
        call.respond(campaigns)
    }
}

// Devolve todas as campanhas que o usuario especificado participa
fun Route.getUserCampaigns() {
    get("userId={userId?}") {
        val userId = call.parameters["userId"] ?: return@get call.respondText(
            "ID do usuario faltando\n",
            status = HttpStatusCode.BadRequest
        )
        if (userStorage.find { it.id == userId.toInt() } == null) return@get call.respondText(
            "Usuario $userId nao existe\n",
            status = HttpStatusCode.NotFound
        )
        val campaigns = campaignStorage.filter { campaign -> campaign.userList.find { it.id == userId.toInt() } != null }
        if (campaigns.isEmpty()) return@get call.respondText(
            "Usuario $userId nao esta em nenhuma campanha\n",
            status = HttpStatusCode.NotFound
        )
        call.respond(campaigns)
    }
}

// Cria uma campanha vazia
fun Route.addCampaign() {
    post("{name?}") {
        val campaignName = call.parameters["name"]?: return@post call.respond(HttpStatusCode.BadRequest)
        val campaign = Campaign(name=campaignName)
        var maxId = -1
        campaignStorage.forEach {
            maxId = if (it.campaignId > maxId) it.campaignId else maxId
        }
        campaign.campaignId = if (campaignStorage.isEmpty()) 0 else maxId + 1
        campaignStorage.add(campaign)
        call.respondText("Campanha criada com sucesso!\n", status = HttpStatusCode.Created)
    }
}

// Deleta campanha especificada
//fun Route.deleteCampaign

// Adiciona o usuario especificado a campanha
fun Route.addUserIdToCampaign() {
    post("addUser/{campaignId?}") {
        val campaignId = call.parameters["campaignId"] ?: return@post call.respond(HttpStatusCode.BadRequest)
        println(campaignId)
        if (campaignStorage.find { it.campaignId == campaignId.toInt() } == null) return@post call.respondText(
            "Campanha com id $campaignId nao existe\n",
            status = HttpStatusCode.NotFound
        )
        val campaign = campaignStorage.find { it.campaignId == campaignId.toInt() }

        // Nao nai ter jeito, vai ter que ter mais gambiarra
        @Serializable
        data class Gambiarra(val userId: Int)
        val userId = call.receive<Gambiarra>().userId

        if (userStorage.find { it.id == userId } == null) return@post call.respondText(
            "Usuario $userId nao existe\n",
            status = HttpStatusCode.NotFound
        )
        if (campaign!!.userList.find { it.id == userId } != null) return@post call.respondText(
            "Usuario $userId ja esta na campanha $campaignId\n",
            status = HttpStatusCode.Conflict
        )
        userStorage.find { it.id == userId }?.let { it1 -> campaign.userList.add(it1) }
        call.respondText("Usuario $userId adicionado a campanha $campaignId com sucesso!")
    }
}

fun Route.addSheetIdToCampaign() {
    post("addSheet/{campaignId?}") {
        val campaignId = call.parameters["campaignId"] ?: return@post call.respond(HttpStatusCode.BadRequest)
        println(campaignId)
        if (campaignStorage.find { it.campaignId == campaignId.toInt() } == null) return@post call.respondText(
            "Campanha com id $campaignId nao existe\n",
            status = HttpStatusCode.NotFound
        )
        val campaign = campaignStorage.find { it.campaignId == campaignId.toInt() }

        // Nao nai ter jeito, vai ter que ter mais gambiarra
        @Serializable
        data class Gambiarra(val sheetId: Int) {}
        val sheetId = call.receive<Gambiarra>().sheetId

        if (sheetStorage.find { it.sheetId == sheetId } == null) return@post call.respondText(
            "Ficha $sheetId nao existe\n",
            status = HttpStatusCode.NotFound
        )
        if (campaign!!.sheetList.find { it.sheetId == sheetId } != null) return@post call.respondText(
            "Ficha $sheetId ja esta na campanha $campaignId\n",
            status = HttpStatusCode.Conflict
        )
        sheetStorage.find { it.sheetId == sheetId }?.let { it1 -> campaign.sheetList.add(it1) }
        call.respondText("Ficha $sheetId adicionada a campanha $campaignId com sucesso!")
    }
}
