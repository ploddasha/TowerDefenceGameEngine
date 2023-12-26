package model.fromEditing

import javafx.beans.property.SimpleListProperty
import javafx.util.Pair
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tornadofx.*

@Serializable
enum class TileType {
    ROAD,
    GRASS,
    WATER,
    CITY
}
@Serializable
data class Tile(
    @SerialName("Row") val row: Int,
    @SerialName("Column") val col: Int,
    @SerialName("Type") var type: TileType,
)

class Map(list: List<Tile>? = null)  {

    val listOfTilesProperty = SimpleListProperty(this, "Tiles", list!!.observable())
    var listOfTiles by listOfTilesProperty

}

class MapModel : ViewModel() {
    val tiles = observableListOf<Tile>().toObservable()

    fun saveInformation(tileMap: MutableMap<Pair<Int, Int>, TileType>) {
        val tileList = tileMap.entries.map { (position, type) ->
            Tile(position.key, position.value, type)
        }
        tiles.setAll(tileList)
    }

    fun addTiles(tiles: List<Tile>) {
        this.tiles.addAll(tiles)
    }

}
