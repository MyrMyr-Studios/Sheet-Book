package com.myrmyr

import com.myrmyr.models.User
import com.myrmyr.plugins.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello, world!", bodyAsText())
        }
    }

    @Test
    fun testUsers() = testApplication {
        application {
            configureRouting()
        }
        val client = createClient {
            this@testApplication.install(ContentNegotiation) {
                json()
            }
        }

        // Confere que nao tem usuarios
        client.get("/users").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Sem usuarios\n", bodyAsText())
        }

        // Adiciona um usuario
        val json = Json.encodeToString(User(name = "Teste", email = "teste@tst.xyz", password = "Teste123"))
        val postResponse: HttpResponse = client.post("/users") {
            contentType(ContentType.Application.Json)
            setBody(json)
        }
        assertEquals("Usuario adicionado com sucesso!\n", postResponse.bodyAsText())
        assertEquals(HttpStatusCode.Created, postResponse.status)

        // Verifica que o usuario foi adicionado
        val getResponse = client.get("/users")
        assertNotEquals("Sem usuarios\n", getResponse.bodyAsText())
        val usersJson = Json.decodeFromString<MutableList<User>>(getResponse.bodyAsText())
        assertEquals(usersJson[0].name, "Teste")
        assertEquals(usersJson[0].email, "teste@tst.xyz")

        // Deleta o usuario
        client.delete("/users/0").apply {
            assertEquals(HttpStatusCode.Accepted, status)
            assertEquals("Usuario 0 removido com sucesso\n", bodyAsText())
        }

        // Confere que nao tem usuarios
        client.get("/users").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Sem usuarios\n", bodyAsText())
        }
    }
}
