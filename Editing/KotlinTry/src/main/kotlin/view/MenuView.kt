package view

import model.MobsModel
import model.map.MapModel
import model.mode.ModeModel
import org.json.JSONArray
import tornadofx.*
import java.io.File

import org.json.JSONObject


class MenuView: View() {
    val mobsModel: MobsModel by inject()
    val mapModel: MapModel by inject()
    val modeModel: ModeModel by inject()


    override val root = vbox (50) {
        label("Выберите с чего начать")
        hbox (10){
            button("Редактировать мобов") {
                action {
                    replaceWith(MobsEditingView::class)
                }
            }
            button("Редактировать башни")
            button("Редактировать карту") {
                action {
                    replaceWith(MapEditingView::class)
                }
            }
            button("Редактировать режим") {
                action {
                    replaceWith(ModeChooseView::class)
                }
            }
            alignment = javafx.geometry.Pos.CENTER
        }
        button("Создать игру") {
            action {
                saveGameData()
            }
        }
        button("Вернуться") {
            action {
                replaceWith(MyView::class)
            }
        }
        alignment = javafx.geometry.Pos.CENTER

    }

    private fun saveGameData() {
        saveMobData()
        saveMobDataJson()

        saveTileData()
        saveTileDataJson()

        saveModeData()
    }

    private fun saveMobData() {
        val file = File("./src/main/resources/MobsData.txt")
        file.writeText("Mobs Data:\n")
        mobsModel.mobsList.forEach { mob ->
            file.appendText("Cost: ${mob.cost}, Damage: ${mob.damage}, Health: ${mob.health}, Type: ${mob.type}, AttackRange: ${mob.attackRange}\n")
        }
        println("Game data saved to MobsData.txt")
    }

    private fun saveTileData() {
        val file = File("./src/main/resources/MapTilesData.txt")
        file.writeText("Map Tiles Data:\n")
        mapModel.tiles.forEach { tile ->
            file.appendText("Row: ${tile.row}, Col: ${tile.col}, Type: ${tile.type}\n")
        }
        println("Tile data saved to MapTilesData.txt")
    }

    private fun saveModeData() {
        val file = File("./src/main/resources/ModeData.txt")
        file.writeText("Mode Data:\n")
        file.writeText("Wave count: ${modeModel.countOfWaves}\n")

        println("Mode data saved to ModeData.txt")
    }


    private fun saveMobDataJson() {
        val file = File("./src/main/resources/MobsData.json")
        val jsonArray = JSONArray()
        mobsModel.mobsList.forEach { mob ->
            val jsonObject = JSONObject()
            jsonObject.put("Cost", mob.cost)
            jsonObject.put("Damage", mob.damage)
            jsonObject.put("Health", mob.health)
            jsonObject.put("Type", mob.type)
            jsonObject.put("AttackRange", mob.attackRange)
            jsonArray.put(jsonObject)
        }
        file.writeText(jsonArray.toString())
        println("Game data saved to MobsData.json")
    }

    private fun saveTileDataJson() {
        val file = File("./src/main/resources/MapTilesData.json")
        val jsonArray = JSONArray()
        mapModel.tiles.forEach { tile ->
            val jsonObject = JSONObject()
            jsonObject.put("Row", tile.row)
            jsonObject.put("Col", tile.col)
            jsonObject.put("Type", tile.type)
            jsonArray.put(jsonObject)
        }
        file.writeText(jsonArray.toString())
        println("Tile data saved to MapTilesData.json")
    }


}