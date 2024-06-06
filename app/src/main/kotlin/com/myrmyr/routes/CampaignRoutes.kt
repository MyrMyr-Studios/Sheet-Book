package com.myrmyr.routes

import com.myrmyr.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.net.URLDecoder
import io.ktor.server.sessions.*
import com.myrmyr.UserSession

fun Route.campaignRouting() {
    route("/campaigns") {
        listAllCampaigns()
        getCampaignById()
        getCampaignsByName()
        getUserCampaigns()
        addCampaign()
        deleteCampaign()
        addUserIdToCampaign()
        deleteUserIdFromCampaign()
        addSheetIdToCampaign()
        deleteSheetIdFromCampaign()
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
        val name = URLDecoder.decode(call.parameters["name"], "UTF-8") ?: return@get call.respondText(
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
        if (userStorage.find { it.userId == userId.toInt() } == null) return@get call.respondText(
            "Usuario $userId nao existe\n",
            status = HttpStatusCode.NotFound
        )
        val campaigns = campaignStorage.filter { userId.toInt() in it.userList }
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
        val campaignName = call.receive<String>()
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
fun Route.deleteCampaign() {
    delete("{id?}") {
        val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
        if (campaignStorage.removeIf { it.campaignId == id.toInt() }) {
            call.respondText("Campanha removida corretamente\n", status = HttpStatusCode.Accepted)
        } else {
            call.respondText("Campanha $id nao foi encontrada\n", status = HttpStatusCode.NotFound)
        }
    }
}

// Adiciona o usuario especificado a campanha especificada
fun Route.addUserIdToCampaign() {
    post("addUser/{campaignId?}") {
        val campaignId = call.parameters["campaignId"] ?: return@post call.respond(HttpStatusCode.BadRequest)
        if (campaignStorage.find { it.campaignId == campaignId.toInt() } == null) return@post call.respondText(
            "Campanha com id $campaignId nao existe\n",
            status = HttpStatusCode.NotFound
        )
        val campaign = campaignStorage.find { it.campaignId == campaignId.toInt() }

        // Nao nai ter jeito, vai ter que ter mais gambiarra
        @Serializable
        data class Gambiarra(val userId: Int)
        val userId = call.receive<Gambiarra>().userId

        if (userStorage.find { it.userId == userId } == null) return@post call.respondText(
            "Usuario $userId nao existe\n",
            status = HttpStatusCode.NotFound
        )
        if (userId in campaign!!.userList) return@post call.respondText(
            "Usuario $userId ja esta na campanha $campaignId\n",
            status = HttpStatusCode.Conflict
        )
        campaign.userList.add(userId)
        call.respondText(
            "Usuario $userId adicionado a campanha $campaignId com sucesso!",
            status = HttpStatusCode.OK
        )
    }
}

// Deleta o usuario especificado da campanha especificada
fun Route.deleteUserIdFromCampaign() {
    delete("{campaignId?}/userId={userId?}") {
        val campaignId = call.parameters["campaignId"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
        if (campaignStorage.find { it.campaignId == campaignId.toInt() } == null) return@delete call.respondText(
            "Campanha com id $campaignId nao existe\n",
            status = HttpStatusCode.NotFound
        )
        val campaign = campaignStorage.find { it.campaignId == campaignId.toInt() }
        val userId = call.parameters["userId"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
        if (userStorage.find { it.userId == userId.toInt() } == null) return@delete call.respondText(
            "Usuario $userId nao existe\n",
            status = HttpStatusCode.NotFound
        )
        if (campaign!!.userList.removeIf { it == userId.toInt() }) {
            call.respondText(
                "Usuario $userId removido corretamente da campanha $campaignId\n",
                status = HttpStatusCode.Accepted
            )
        } else {
            call.respondText(
                "Usuario $userId nao esta na campanha $campaignId\n",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

// Adiciona a ficha especificada a campanha especificada
fun Route.addSheetIdToCampaign() {
    post("addSheet/{campaignId?}") {
        val campaignId = call.parameters["campaignId"] ?: return@post call.respond(HttpStatusCode.BadRequest)
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
        if (sheetId in campaign!!.sheetList) return@post call.respondText(
            "Ficha $sheetId ja esta na campanha $campaignId\n",
            status = HttpStatusCode.Conflict
        )
        campaign.sheetList.add(sheetId)
        call.respondText(
            "Ficha $sheetId adicionada a campanha $campaignId com sucesso!\n",
            status = HttpStatusCode.OK
        )
    }
}

// Deleta a ficha especificada da campanha especificada
fun Route.deleteSheetIdFromCampaign() {
    delete("{campaignId?}/sheetId={sheetId?}") {
        val campaignId = call.parameters["campaignId"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
        if (campaignStorage.find { it.campaignId == campaignId.toInt() } == null) return@delete call.respondText(
            "Campanha com id $campaignId nao existe\n",
            status = HttpStatusCode.NotFound
        )
        val campaign = campaignStorage.find { it.campaignId == campaignId.toInt() }
        val sheetId = call.parameters["sheetId"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
        println(sheetId)
        if (sheetStorage.find { it.sheetId == sheetId.toInt() } == null) return@delete call.respondText(
            "Ficha $sheetId nao existe\n",
            status = HttpStatusCode.NotFound
        )
        if (campaign!!.sheetList.removeIf { it == sheetId.toInt() }) {
            call.respondText(
                "Ficha $sheetId removida corretamente da campanha $campaignId\n",
                status = HttpStatusCode.Accepted
            )
        } else {
            call.respondText(
                "Ficha $sheetId nao esta na campanha $campaignId\n",
                status = HttpStatusCode.NotFound
            )
        }
    }
}