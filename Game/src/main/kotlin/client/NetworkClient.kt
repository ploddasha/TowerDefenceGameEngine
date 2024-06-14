package client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import javax.json.Json


class NetworkClient {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
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

    suspend fun getOpponentState() {
        val response = client.get("http://localhost:8083/state") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
        val responseBody = response.body<String>()
        println(responseBody)
    }
}



@Serializable
data class Game(val id: Int, val gameName: String)
