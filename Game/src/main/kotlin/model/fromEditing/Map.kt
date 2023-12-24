package model.fromEditing

import javafx.beans.property.SimpleListProperty
import javafx.util.Pair
import tornadofx.*

data class Tile(val row: Int, val col: Int, var type: String)

class Map(list: List<Tile>? = null)  {

    val listOfTilesProperty = SimpleListProperty(this, "Tiles", list!!.observable())
    var listOfTiles by listOfTilesProperty

}

class MapModel : ViewModel() {
    val tiles = observableListOf<Tile>().toObservable()

    /*
    fun saveInformation(tileMap: MutableMap<Pair<Int, Int>, String>) {
        val tileList = tileMap.entries.map { (position, type) ->
            Tile(position.first, position.second, type)
        }
        tiles.setAll(tileList)
    } */
}
