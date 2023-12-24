package viewModel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
//import model.fromEditing.Mob
//import model.fromEditing.MobType
import java.io.File

@Serializable
enum class MobType {
    Fly, Walk
}

@Serializable
data class Mob(
    @SerialName("Cost") val cost: Int,
    @SerialName("Damage") val damage: Int,
    @SerialName("Health") val health: Int,
    @SerialName("Speed") val speed: Int,
    @SerialName("AttackRange") val attackRange: Int,
    @SerialName("Type") val type: MobType
)

fun loadMobDataJson(): List<Mob> {
    val file = File("./src/main/resources/configs/fromEditing/MobsData.json")
    println(file.readText())
    val result =  Json.decodeFromString<List<Mob>>(file.readText())

    return result
}