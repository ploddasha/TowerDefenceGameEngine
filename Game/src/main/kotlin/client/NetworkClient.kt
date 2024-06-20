package client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import model.data.Game
import model.data.GameState


class NetworkClient {

    val server: String = "http://localhost:8083"

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getAllGames(): List<Game> {

        val response = client.get("$server/games") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }

        if (response.status == HttpStatusCode.OK) {
            val data = response.body<List<Game>>()
            println(data)
            return data
        } else {
            println("Error with sending request")
            return emptyList()
        }
    }

    suspend fun getOpponentState() {
        val response = client.get("$server/state") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
        val responseBody = response.body<String>()
        println(responseBody)
    }

    @OptIn(InternalAPI::class)
    suspend fun sendGameState(gameState: GameState) {
        val response = client.post("$server/updateState") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            //Здесь необходимо отправить своё состояние
            body = "{\"key\": \"value\"}"
        }
        val responseBody = response.body<String>()
        println(responseBody)
    }

    suspend fun connect(): Boolean {
        val response = client.get("$server/check")
        val responseBody = response.body<String>()
        return responseBody.toBoolean()
    }

}

