package client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import model.data.Game
import model.data.GameState
import model.data.UnifiedGameData


class NetworkClient {

    //private val server: String = "http://10.40.109.67:8083"
    private val server: String = "http://192.168.0.109:8083"



    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000 // 5 seconds timeout
            connectTimeoutMillis = 5000
            socketTimeoutMillis = 5000
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

    suspend fun getGameByName(name: String): UnifiedGameData? {
        val response = client.get("$server/getGame") {
            parameter("name", name)
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }

        return if (response.status == HttpStatusCode.OK) {
            response.body<UnifiedGameData>()
        } else {
            println("Error fetching game: ${response.status}")
            null
        }
    }
    suspend fun getAllPlayers(): List<String> {
        val response = client.get("$server/getPlayers")
        val players = response.body<String>()
        return players.split("\n")
    }

    suspend fun getPlayerGames(player: String): String {
        val response = client.get("$server/getListOfGames") {
            parameter("player", player)
        }
        return response.body<String>()
    }
    suspend fun getPlayerScores(player: String): String {
        val response = client.get("$server/getScores") {
            parameter("player", player)
        }
        return response.body<String>()
    }
    suspend fun getPlayer(player: String): String {
        val response = client.get("$server/getPlayer") {
            parameter("player", player)
        }
        return response.body<String>()
    }
}

