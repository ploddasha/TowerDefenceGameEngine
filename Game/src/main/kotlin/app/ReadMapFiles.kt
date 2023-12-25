package app


import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.fromEditing.*
import model.fromEditing.Tile
import java.io.File


fun loadMapDataJson(): List<Tile> {
    val file = File("./src/main/resources/configs/fromEditing/MapData.json")
    println(file.readText())
    val result =  Json.decodeFromString<List<Tile>>(file.readText())

    return result
}


fun createMapModel(mapModel: MapModel) {
    val listOfTiles = loadMapDataJson()
    mapModel.addTiles(listOfTiles)

}