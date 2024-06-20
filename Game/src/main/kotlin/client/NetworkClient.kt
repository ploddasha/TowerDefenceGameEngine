package client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.serialization.Serializable
import model.GameState
import model.data.Game
import org.apache.http.HttpResponse
import javax.json.Json


class NetworkClient {

    val server: String = "http://192.168.0.134:8083"

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
    suspend fun sendGameState() {
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

}

