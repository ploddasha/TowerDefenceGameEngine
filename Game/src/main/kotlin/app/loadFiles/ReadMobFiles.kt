package app.loadFiles

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.fromEditing.MobType
import model.fromEditing.MobsModel
//import model.fromEditing.Mob
//import model.fromEditing.MobType
import java.io.File

/*
@Serializable
enum class MobType {
    Fly, Walk
}
 */

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


fun createMobModel(mobsModel: MobsModel) {

    val listOfMobs = loadMobDataJson()
    for (i in listOfMobs.indices) {
        mobsModel.addMob(model.fromEditing.Mob(
            cost = listOfMobs[i].cost,
            damage = listOfMobs[i].damage,
            health = listOfMobs[i].damage,
            speed = listOfMobs[i].speed,
            attackRange = listOfMobs[i].attackRange,
            type = listOfMobs[i].type
        ))
    }

}