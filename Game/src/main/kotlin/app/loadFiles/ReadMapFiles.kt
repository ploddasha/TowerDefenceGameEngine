package app.loadFiles


import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.fromEditing.*
import model.fromEditing.Tile
import java.io.File


fun loadMapDataJson(path: String): List<Tile> {
    val file = File(path)
    println(file.readText())
    val result =  Json.decodeFromString<List<Tile>>(file.readText())

    return result
}


fun createMapModel(mapModel: MapModel, path: String) {
    val listOfTiles = loadMapDataJson(path)
    mapModel.addTiles(listOfTiles)

}