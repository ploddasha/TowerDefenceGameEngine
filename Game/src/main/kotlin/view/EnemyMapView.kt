package view

import app.loadFiles.createMapModel
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import model.TilePair
import model.fromEditing.MapModel
import model.fromEditing.MobType
import model.fromEditing.TileType
import tornadofx.*
import viewModel.EnemyGameController
import viewModel.GameController
import viewModel.real.RealMob
import viewModel.real.RealTower
import viewModel.real.TowerType

class EnemyMapView(
    private val gameController: EnemyGameController
) : View() {

    private val grass = "/configs/fromEditing/map/grass.png"
    private val sand = "/configs/fromEditing/map/sand.png"
    private val water = "/configs/fromEditing/map/water.png"
    private val city = "/configs/fromEditing/map/city.jpg"
    private val walkMobImage = "/configs/fromEditing/map/mushroom.png"
    private val flyMobImage = "/configs/fromEditing/map/flying_mob.png"

    private val towerImage1 = "/configs/tower1.png"
    private val towerImage2 = "/configs/tower2.png"
    private val towerImage3 = "/configs/tower3.png"


    private val numRows = 10
    private val numCols = 10
    private val cellSize = 50.0

    private val mapModel: MapModel by inject()
    private val mobPositions = mutableMapOf<Int, Pair<Int, Int>>()


    init {
        val gameId = 1
        createMapModel(mapModel, "./src/main/resources/configs/games/game$gameId/MapData.json")
    }


    override val root = gridpane {
        val tilesArray  = mapModel.getArray(numRows, numCols)

        hgap = 1.0
        vgap = 1.0
        paddingAll = 0.0

        addClass("game-field-background")

        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                val tile = tilesArray[row][col]

                val cellImageView = when (tile.tileType) {
                    TileType.START -> ImageView(Image(resources.url(city).toString()))
                    TileType.ROAD -> ImageView(Image(resources.url(sand).toString()))
                    TileType.GRASS -> ImageView(Image(resources.url(grass).toString()))
                    TileType.WATER -> ImageView(Image(resources.url(water).toString()))
                    TileType.CITY -> ImageView(Image(resources.url(city).toString()))
                    else -> ImageView(Image(resources.url(grass).toString()))
                }
                cellImageView.isPreserveRatio = true
                cellImageView.fitWidth = cellSize
                cellImageView.fitHeight = cellSize

                add(cellImageView, col, row)

            }
        }
    }


    fun deleteMobFromMap(row: Int, col: Int) {
        val gridPane = root as GridPane
        println("Удаляем")

        val tilesArray  = mapModel.getArray(numRows, numCols)
        val tile = tilesArray[row][col]

        val cellImageView = when (tile.tileType) {
            TileType.START -> ImageView(Image(resources.url(city).toString()))
            TileType.ROAD -> ImageView(Image(resources.url(sand).toString()))
            TileType.GRASS -> ImageView(Image(resources.url(grass).toString()))
            TileType.WATER -> ImageView(Image(resources.url(water).toString()))
            TileType.CITY -> ImageView(Image(resources.url(city).toString()))
            else -> ImageView(Image(resources.url(grass).toString()))
        }
        cellImageView.isPreserveRatio = true
        cellImageView.fitWidth = cellSize
        cellImageView.fitHeight = cellSize
        gridPane.add(cellImageView, col, row)
    }



    fun addTower(tower: RealTower) {
        val gridPane = root

        var image: String = towerImage2
        image = if (tower.type == TowerType.FlyingTower) {
            towerImage3
        } else {
            towerImage1
        }

        val cellImageView = ImageView(Image(resources.url(image).toString()))
        cellImageView.isPreserveRatio = true
        cellImageView.fitWidth = cellSize
        cellImageView.fitHeight = cellSize

        gridPane.add(cellImageView, tower.col, tower.row)
    }

    fun addMob(mob: RealMob) {
        val gridPane = root
        val tilesArray  = mapModel.getArray(numRows, numCols)

        val currentPos = mobPositions[mob.id]
        if (currentPos != null && (currentPos.first != mob.row || currentPos.second != mob.col)) {
            deleteMobFromMap(currentPos.first, currentPos.second)
        }

        var mobImage = ""
        if (mob.type == MobType.Fly) {
            mobImage = flyMobImage
        } else if (mob.type == MobType.Walk) {
            mobImage = walkMobImage
        }
        val cellImageView = ImageView(Image(resources.url(mobImage).toString()))
        cellImageView.isPreserveRatio = true
        cellImageView.fitWidth = cellSize
        cellImageView.fitHeight = cellSize

        gridPane.add(cellImageView, mob.col, mob.row)
        mobPositions[mob.id] = Pair(mob.row, mob.col)

    }

}

