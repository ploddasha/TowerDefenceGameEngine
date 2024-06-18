package app.loadFiles


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.fromEditing.TowersModel
import java.io.File



@Serializable
data class Tower(
    @SerialName("Cost") val cost: Int,
    @SerialName("Damage") val damage: Int,
    @SerialName("Health") val health: Int,
    @SerialName("Speed") val speed: Int,
    @SerialName("AttackRange") val attackRange: Int,
)

fun loadTowerDataJson(): List<Tower> {
    val file = File("./src/main/resources/configs/fromEditing/TowerData.json")
    println(file.readText())
    val result =  Json.decodeFromString<List<Tower>>(file.readText())

    return result
}


fun createTowersModel(towersModel: TowersModel) {

    val listOfTowers = loadTowerDataJson()
    for (i in listOfTowers.indices) {
        towersModel.addTower(model.fromEditing.Tower(
            cost = listOfTowers[i].cost,
            damage = listOfTowers[i].damage,
            health = listOfTowers[i].health,
            speed = listOfTowers[i].speed,
            attackRange = listOfTowers[i].attackRange
        ))
    }

}