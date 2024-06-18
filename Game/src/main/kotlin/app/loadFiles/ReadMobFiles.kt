package app.loadFiles

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.fromEditing.MobType
import model.fromEditing.MobsModel
import java.io.File
@Serializable
data class Mob(
    @SerialName("Cost") val cost: Int,
    @SerialName("Damage") val damage: Int,
    @SerialName("Health") val health: Int,
    @SerialName("Speed") val speed: Int,
    @SerialName("AttackRange") val attackRange: Int,
    @SerialName("Type") val type: MobType,
    @SerialName("Name") val name: String
)

fun loadMobDataJson(path: String): List<Mob> {
    val file = File(path)
    println(file.readText())
    val result =  Json.decodeFromString<List<Mob>>(file.readText())

    return result
}


fun createMobModel(mobsModel: MobsModel, path: String) {

    val listOfMobs = loadMobDataJson(path)

    for (i in listOfMobs.indices) {
        mobsModel.addMob(model.fromEditing.Mob(
            cost = listOfMobs[i].cost,
            damage = listOfMobs[i].damage,
            health = listOfMobs[i].health,
            speed = listOfMobs[i].speed,
            attackRange = listOfMobs[i].attackRange,
            type = listOfMobs[i].type,
            name = listOfMobs[i].name
        ))
    }

}