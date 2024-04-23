package view

import app.loadFiles.createMapModel
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import model.fromEditing.MapModel
import model.fromEditing.TileType
import tornadofx.View
import tornadofx.gridpane
import tornadofx.paddingAll
import viewModel.real.RealMob

class MapView : View() {

    private val grass = "/configs/fromEditing/map/grass.png"
    private val sand = "/configs/fromEditing/map/sand.png"
    private val water = "/configs/fromEditing/map/water.png"
    private val city = "/configs/fromEditing/map/city.jpg"
    private val mobImage = "/configs/fromEditing/map/mushroom.png"

    private val numRows = 10
    private val numCols = 10

    private val mapModel: MapModel by inject()


    init {
        createMapModel(mapModel)
    }


    override val root = gridpane {
        val tilesArray  = mapModel.getArray(numRows, numCols)

        hgap = 1.0
        vgap = 1.0
        paddingAll = 0.0

        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                val tile = tilesArray[row][col]

                val cellImageView = when (tile.tileType) {
                    TileType.ROAD -> ImageView(Image(resources.url(sand).toString()))
                    TileType.GRASS -> ImageView(Image(resources.url(grass).toString()))
                    TileType.WATER -> ImageView(Image(resources.url(water).toString()))
                    TileType.CITY -> ImageView(Image(resources.url(city).toString()))
                    null -> ImageView(Image(resources.url(grass).toString()))
                }
                cellImageView.isPreserveRatio = true
                cellImageView.fitWidth = 20.0
                cellImageView.fitHeight = 20.0

                add(cellImageView, col, row)
            }
        }
    }


    private val mobPredPositions = mutableMapOf<Int, Pair<Int, Int>>()


    fun canMoveTo(tileType: TileType): Boolean {
        return tileType == TileType.ROAD
    }

    fun add(mob: RealMob) {
        val gridPane = root as GridPane

        val tilesArray  = mapModel.getArray(numRows, numCols)
        val nextTile = tilesArray[mob.row + 1][mob.col]
        if (canMoveTo(nextTile.tileType)) {
            mob.move(1, 0)
            println("can move and move down ${mob.row} ${mob.col}")
        } else {
            mob.move(0, 1)
            println("can move and move right ${mob.row} ${mob.col}")

        }


        if (mob.id in mobPredPositions) {
            val predPair = mobPredPositions[mob.id]
            val tile = tilesArray[predPair?.second!!][predPair.first]

            val cellImageView = when (tile.tileType) {
                TileType.ROAD -> ImageView(Image(resources.url(sand).toString()))
                TileType.GRASS -> ImageView(Image(resources.url(grass).toString()))
                TileType.WATER -> ImageView(Image(resources.url(water).toString()))
                TileType.CITY -> ImageView(Image(resources.url(city).toString()))
                null -> ImageView(Image(resources.url(grass).toString()))
            }
            cellImageView.isPreserveRatio = true
            cellImageView.fitWidth = 20.0
            cellImageView.fitHeight = 20.0

            gridPane.add(cellImageView, predPair.first, predPair.second)
        }

        val cellImageView = ImageView(Image(resources.url(mobImage).toString()))
        cellImageView.isPreserveRatio = true
        cellImageView.fitWidth = 20.0
        cellImageView.fitHeight = 20.0

        gridPane.add(cellImageView, mob.col, mob.row)

        mobPredPositions[mob.id] = Pair(mob.col, mob.row)
    }




}