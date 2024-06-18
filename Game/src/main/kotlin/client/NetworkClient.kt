package client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import model.GameState


class NetworkClient {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun sendGameState(gameState: GameState) {
        val response = client.post("http://localhost:8083/updateState") {
            contentType(ContentType.Application.Json)
            setBody(gameState)
        }

        if (response.status == HttpStatusCode.OK) {
            println("Game state sent successfully")
        } else {
            println("Error sending game state")
        }
    }

    suspend fun getAllGames() {

        val response = client.get("http://localhost:8083/games") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }

        if (response.status == HttpStatusCode.OK) {
            val data = response.body<List<Game>>()
            println(data)
        } else {
            println("Error with sending request")
        }
    }

    suspend fun getState(): String {
        val response = client.get("http://localhost:8083/state") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Text.Plain)
            }
        }
        return if (response.status == HttpStatusCode.OK) {
            response.bodyAsText()
        } else {
            println("Error fetching state")
            ""
        }
    }

    suspend fun checkConnection(): Boolean {
        val response = client.get("http://localhost:8083/check") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
        return if (response.status == HttpStatusCode.OK) {
            response.body()
        } else {
            println("Error checking connection")
            false
        }
    }
}



@Serializable
data class Game(val id: Int, val gameName: String)
