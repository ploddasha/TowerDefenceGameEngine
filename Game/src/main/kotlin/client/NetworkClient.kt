package client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import model.data.Game
import model.data.GameState


class NetworkClient {

    val server: String = "http://10.40.109.67:8083"


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

    suspend fun getOpponentState(): GameState? {
        val response = client.get("$server/state") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
        return try {
            if (response.status == HttpStatusCode.OK && response.contentLength() != 0L) {
                response.body<GameState>()
            } else {
                println("Error with receiving opponent state: No content")
                null
            }
        } catch (e: NoTransformationFoundException) {
            println("Error with receiving opponent state: ${e.message}")
            null
        }
    }

    suspend fun sendGameState(gameState: GameState): Boolean {
        val response = client.post("$server/updateState") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            setBody(gameState)
        }
        return response.status == HttpStatusCode.OK
    }


    /*suspend fun connect(name: String): Boolean {
        val response = client.get("$server/check")
        val responseBody = response.body<String>()
        return responseBody.toBoolean()
    }*/

    suspend fun connect(name: String): Boolean {
        val response = client.get("$server/connect") {
            parameter("name", name)
        }
        val responseBody = response.body<String>()
        return responseBody.toBoolean()
    }

    suspend fun check(): Boolean {
        val response = client.get("$server/check")
        val responseBody = response.body<String>()
        return responseBody.toBoolean()
    }

    suspend fun getGameSettings(name: String): String {
        val response = client.get("$server/getGame") {
            parameter("name", name)
        }
        return response.body<String>()
    }

    suspend fun getRating(name: String): String{
        val response = client.get("$server/rating") {
            parameter("name", name)
        }
        return response.body<String>()
    }

    suspend fun addToRating(name: String, player: String, result: Int): Boolean {
        val response = client.post("$server/addToRating"){
            parameter("name", name)
            setBody("$player $result")
        }
        return response.status == HttpStatusCode.OK
    }
}

