package view

import app.loadFiles.createMapModel
import app.loadFiles.createMobModel
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import model.fromEditing.MapModel
import model.fromEditing.Mob
import model.fromEditing.MobsModel
import model.fromEditing.TileType
import tornadofx.*
import viewModel.real.GameController
import viewModel.real.RealMob

class GameView : View("Bashenki") {

    val mobsModel: MobsModel by inject()
    val mapModel: MapModel by inject()

    val gameController: GameController by inject()


    private val tileMap = mutableMapOf<Pair<Int, Int>, String>()

    val grass = "/configs/fromEditing/map/grass.png"
    val sand = "/configs/fromEditing/map/sand.png"
    val water = "/configs/fromEditing/map/water.png"
    val city = "/configs/fromEditing/map/city.jpg"
    val mobImage = "/configs/fromEditing/map/mushroom.png"

    private val tiles = listOf(
        grass, sand, water, city
    )

    data class Tile(val x: Int, val y: Int, val width: Double, val height: Double)

    private val tileSize = 20.0
    private val numRows = 10
    private val numCols = 10

    init {
        gameController.startMobMovement()
    }
    private val newMob = RealMob(0, 0)




    override val root = stackpane {

        addClass("game-stack-pane")

        vbox {

            paddingTop = 10.0
            paddingRight = 7.0
            alignment = Pos.TOP_RIGHT

            //to show that read json works
            createMobModel(mobsModel)
            label(text = mobsModel.mobsList[0].cost.toString())
            createMapModel(mapModel)
            label(text = mapModel.tiles[0].type.toString())

            button("Shop") {
                style {
                    fontSize = 14.px
                    padding = box(5.px, 10.px)
                    paddingAll = 5.0
                    backgroundColor += Color.GREEN
                    textFill = Color.WHITE
                    fontWeight = FontWeight.BOLD
                }

                action {
                    add(ShopView())
                }
            }
            button("Pause") {
                style {
                    fontSize = 14.px
                    padding = box(5.px, 10.px)
                    paddingAll = 5.0
                    backgroundColor += Color.rgb(255, 152, 0)
                    textFill = Color.WHITE
                    fontWeight = FontWeight.BOLD
                }

                action {
                    add(PauseMenuView())
                }
            }
        }

        gridpane {

            hgap = 1.0
            vgap = 1.0
            paddingAll = 0.0

            repeat(numRows) { row ->
                repeat(numCols) { col ->
                    val tile = mapModel.tiles.find { it.row == row && it.col == col }

                    val cellImageView = when (tile?.type) {
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

            //gameController.addMob(newMob.x, newMob.y)

            // Отображаем мобов
            val cellImageView = ImageView(Image(resources.url(mobImage).toString()))
            cellImageView.isPreserveRatio = true
            cellImageView.fitWidth = 20.0
            cellImageView.fitHeight = 20.0
            add(cellImageView, newMob.x, newMob.y)

        }


    }

    private fun handleTileClick(cellImageView: ImageView, col: Int, row: Int) {

    }

}
