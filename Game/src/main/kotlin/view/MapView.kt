package view

import app.loadFiles.createMapModel
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import model.TilePair
import model.fromEditing.MapModel
import model.fromEditing.MobType
import model.fromEditing.TileType
import model.fromEditing.TowerType
import tornadofx.*
import viewModel.GameController
import viewModel.real.RealMob

class MapView(
    private val gameController: GameController
    ) : View() {

    private val grass = "/configs/fromEditing/map/grass.png"
    private val sand = "/configs/fromEditing/map/sand.png"
    private val water = "/configs/fromEditing/map/water.png"
    private val city = "/configs/fromEditing/map/city.jpg"
    private val walkMobImage = "/configs/fromEditing/map/mushroom.png"
    private val flyMobImage = "/configs/fromEditing/map/flying_mob.png"


    private val numRows = 10
    private val numCols = 10
    private val cellSize = 50.0

    private val mapModel: MapModel by inject()


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
                if (tile.tileType == TileType.START) {
                    gameController.addStart(tile.x, tile.y)
                }
                if (tile.tileType == TileType.CITY) {
                    gameController.addCity(tile.x, tile.y)
                }

                cellImageView.isPreserveRatio = true
                cellImageView.fitWidth = cellSize
                cellImageView.fitHeight = cellSize

                add(cellImageView, col, row)

                cellImageView.setOnMouseClicked {
                    handleTileClick(col, row)
                }
            }
        }
    }


    //private val mobPredPositions = mutableMapOf<Int, Pair<Int, Int>>()
    //private val visited = mutableSetOf<TilePair>()

    fun clearPredMob() {

    }

    private fun getTileToMove(tilesArray: Array<Array<GameView.GameTile>>, mob: RealMob): TilePair? {
        val possible = mutableListOf<TilePair>()
        val maxRow = tilesArray.size - 1
        val maxCol = tilesArray[0].size - 1

        // Проверка движения вниз
        if (mob.row + 1 <= maxRow &&
            (tilesArray[mob.row + 1][mob.col].tileType == TileType.ROAD ||
                    tilesArray[mob.row + 1][mob.col].tileType == TileType.CITY) &&
            !mob.visited.contains(TilePair(mob.row + 1, mob.col))) {
            possible.add(TilePair(mob.row + 1, mob.col))
        }

        // Проверка движения вправо
        if (mob.col + 1 <= maxCol &&
            (tilesArray[mob.row][mob.col + 1].tileType == TileType.ROAD ||
                    tilesArray[mob.row][mob.col + 1].tileType == TileType.CITY) &&
            !mob.visited.contains(TilePair(mob.row, mob.col + 1))) {
            possible.add(TilePair(mob.row, mob.col + 1))
        }
        /*
        // Проверка движения вверх
        if (mob.row - 1 >= 0 &&
            (tilesArray[mob.row - 1][mob.col].tileType == TileType.ROAD ||
                    tilesArray[mob.row - 1][mob.col].tileType == TileType.CITY) &&
            !mob.visited.contains(TilePair(mob.row - 1, mob.col))) {
            possible.add(TilePair(mob.row - 1, mob.col))
        }

        // Проверка движения влево
        if (mob.col - 1 >= 0 &&
            (tilesArray[mob.row][mob.col - 1].tileType == TileType.ROAD ||
                    tilesArray[mob.row][mob.col - 1].tileType == TileType.CITY) &&
            !mob.visited.contains(TilePair(mob.row, mob.col - 1))) {
            possible.add(TilePair(mob.row, mob.col - 1))
        } */

        if (possible.isNotEmpty()) {
            return possible.random()
        }

        return null
    }


    fun add(mob: RealMob) {
        val gridPane = root as GridPane

        val tilesArray  = mapModel.getArray(numRows, numCols)

        if (mob.health > 0) {
            val tileToMove = getTileToMove(tilesArray, mob)
            tileToMove?.let { mob.moveTo(tileToMove.first, tileToMove.second) }
            if (tileToMove != null) {
                mob.visited.add(TilePair(tileToMove.first, tileToMove.second))
            }

        }

        if (mob.id in mob.mobPredPositions) {
            val predPair = mob.mobPredPositions[mob.id]
            val tile = tilesArray[predPair?.second!!][predPair.first]

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

            gridPane.add(cellImageView, predPair.first, predPair.second)
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

        mob.mobPredPositions[mob.id] = Pair(mob.col, mob.row)
    }

    private fun handleTileClick(col: Int, row: Int) {
        val gridPane = root as GridPane

        val tower = gameController.getTowerToPut()
        if (tower != null) {

            if (tower.type == TowerType.Fly) {
                gameController.createRealFlyingTower(tower, col, row)
            } else if (tower.type == TowerType.Walk) {
                gameController.createRealGroundTower(tower, col, row)
            } else if (tower.type == TowerType.Both) {
                gameController.createRealBothTower(tower, col, row)
            }
            println("Ставим башню")

            val currTowerImagePath = "/configs/"
            val imageOfTowerToPut: String = currTowerImagePath + tower.fileName

            val cellImageView = ImageView(Image(resources.url(imageOfTowerToPut).toString()))
            cellImageView.isPreserveRatio = true
            cellImageView.fitWidth = cellSize
            cellImageView.fitHeight = cellSize
            gridPane.add(cellImageView, col, row)

            gameController.removeTowerToPut()
        }
    }

    fun deleteMobFromMap(row: Int, col: Int) {
        val gridPane = root as GridPane
        println("Удаляем")

        val tilesArray  = mapModel.getArray(numRows, numCols)
        val tile = tilesArray[row][col]

        val cellImageView = when (tile.tileType) {
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

}

