package com.myrmyr.routes

import com.myrmyr.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.campaignRouting() {
    route("/campaigns") {
        listAllCampaigns()
        getCampaignById()
        getCampaignsByName()
        getUserCampaigns()
        addCampaign()
        addUserIdToCampaign()
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
        val campaigns = campaignStorage.filter { campaign -> campaign.userList.find { it == userId.toInt() } != null }
        if (campaigns.isEmpty()) return@get call.respondText(
            "Usuario $userId nao esta em nenhuma campanha\n",
            status = HttpStatusCode.NotFound
        )
        call.respond(campaigns)
    }
}

// Cria uma campanha vazia
fun Route.addCampaign() {
    post {
        val campaign = call.receive<Campaign>()
        var maxId = -1
        campaignStorage.forEach {
            maxId = if (it.campaignId > maxId) it.campaignId else maxId
        }
        campaign.campaignId = if (campaignStorage.isEmpty()) 0 else maxId + 1
        campaignStorage.add(campaign)
        call.respondText("Campanha criada com sucesso!\n", status = HttpStatusCode.Created)
    }
}

// Adiciona o usuario especificado a campanha
fun Route.addUserIdToCampaign() {
    post("campaignId={campaignId?}&userId={userId?}") {
        val campaignId = call.parameters["campaignId"] ?: return@post call.respond(HttpStatusCode.BadRequest)
        if (campaignStorage.find { it.campaignId == campaignId.toInt() } == null) return@post call.respondText(
            "Campanha com id $campaignId nao existe\n",
            status = HttpStatusCode.NotFound
        )
        val campaign = campaignStorage.find { it.campaignId == campaignId.toInt() }
        val userId = call.parameters["userId"] ?: return@post call.respond(HttpStatusCode.BadRequest)
        if (userId.toInt() in campaign!!.userList) return@post call.respondText(
            "Usuario $userId ja esta na campanha $campaignId\n",
            status = HttpStatusCode.Conflict
        )
        campaign.userList.add(userId.toInt())
        call.respondText("Usuario $userId adicionado a campanha $campaignId com sucesso!")
    }
}

fun Route.addSheetIdToCampaign() {

}
