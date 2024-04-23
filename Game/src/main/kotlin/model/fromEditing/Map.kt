package model.fromEditing

import javafx.beans.property.SimpleListProperty
import javafx.util.Pair
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tornadofx.*
import view.GameView

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

    val roadCoords = observableListOf<Pair<Int, Int>>().toObservable()

    fun saveInformation(tileMap: MutableMap<Pair<Int, Int>, TileType>) {
        val tileList = tileMap.entries.map { (position, type) ->
            Tile(position.key, position.value, type)
        }
        tiles.setAll(tileList)
    }

    fun addTiles(tiles: List<Tile>) {
        this.tiles.addAll(tiles)
    }

    fun getArray(rows: Int, cols: Int): Array<Array<GameView.GameTile>> {
        val array = Array(rows) { Array(cols) { GameView.GameTile(0, 0, 1.0, 1.0, TileType.GRASS) } }

        for (tile in tiles) {
            array[tile.row][tile.col] = GameView.GameTile(tile.row, tile.col, 1.0, 1.0, tile.type)
            if (tile.type == TileType.ROAD) {
                roadCoords.add(Pair(tile.row, tile.col))
            }
        }
        return array
    }

}
