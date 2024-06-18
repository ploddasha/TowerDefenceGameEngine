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
import org.apache.http.HttpResponse
import javax.json.Json


class NetworkClient {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getAllGames() {

        val response = client.get("http://192.168.0.134:8083/games") {
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
        val response = client.get("http://192.168.0.134:8083/state") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
        val responseBody = response.body<String>()
        println(responseBody)
    }

    @OptIn(InternalAPI::class)
    suspend fun updateState() {
        val response = client.post("http://192.168.0.134:8083/updateState") {
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
        val response = client.get("http://192.168.0.134:8083/check")
        val responseBody = response.body<String>()
        return responseBody.toBoolean()
    }
}



@Serializable
data class Game(val id: Int, val gameName: String)
