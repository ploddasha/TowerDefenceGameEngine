package viewModel.towerControllers

import app.loadFiles.Mob
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import java.io.File
import tornadofx.*
import java.nio.file.Paths

data class Walk(
    val Type: String,
    val Health: Int,
    val FileName: String,
    val Damage: Int,
    val Range: Int,
    val Cost: Int,
    val Name: String)

data class Fly(
    val Type: String,
    val Health: Int,
    val FileName: String,
    val Damage: Int,
    val Range: Int,
    val Cost: Int,
    val Name: String)

private val walkList = mutableListOf<Walk>()
private val flyList = mutableListOf<Fly>()
private var isParsed = false

private fun parse() {
    val file = File("./src/main/resources/configs/fromEditing/TowersData.json")
    val jsonString = file.readText()

    val jsonArray = JSONArray(jsonString)

    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        val type = jsonObject.getString("Type")
        val health = jsonObject.getInt("Health")
        val fileName = jsonObject.getString("FileName")
        val damage = jsonObject.getInt("Damage")
        val range = jsonObject.getInt("Range")
        val cost = jsonObject.getInt("Cost")
        val name = jsonObject.getString("Name")

        if (type == "Walk") {
            val walk = Walk(type, health, fileName, damage, range, cost, name)
            walkList.add(walk)
        } else if (type == "Fly") {
            val fly = Fly(type, health, fileName, damage, range, cost, name)
            flyList.add(fly)
        }
    }
    isParsed = true
}

fun parseWalk(): MutableList<Walk> {
    if (!isParsed) {
        parse()
    }
    return walkList
}

fun parseFly(): MutableList<Fly> {
    if (!isParsed) {
        parse()
    }
    return flyList
}