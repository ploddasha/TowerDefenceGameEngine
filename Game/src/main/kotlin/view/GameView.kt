package view

import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import model.fromEditing.MobsModel
import tornadofx.*
import viewModel.createMobModel

class GameView : View("Bashenki") {

    val mobsModel: MobsModel by inject()

    private val tileMap = mutableMapOf<Pair<Int, Int>, String>()

    val grass = "/configs/fromEditing/map/grass.png"
    val sand = "/configs/fromEditing/map/sand.png"
    val water = "/configs/fromEditing/map/water.png"
    val city = "/configs/fromEditing/map/city.jpg"
    private val tiles = listOf(
        grass, sand, water, city
    )

    data class Tile(val x: Int, val y: Int, val width: Double, val height: Double)

    private val tileSize = 20.0
    private val numRows = 10
    private val numCols = 10


    override val root = stackpane {

        addClass("game-stack-pane")

        vbox {

            paddingTop = 10.0
            paddingRight = 7.0
            alignment = Pos.TOP_RIGHT

            createMobModel(mobsModel)
            //to show that read json works
            label(text = mobsModel.mobsList[0].cost.toString())

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

        /*
        gridpane {
            hgap = 1.0
            vgap = 1.0
            paddingAll = 0.0

            repeat(20) { row ->
                repeat(20) { col ->
                    val cellImageView = ImageView(Image(resources.url(grass).toString()))
                    cellImageView.isPreserveRatio = true
                    cellImageView.fitWidth = 20.0
                    cellImageView.fitHeight = 20.0
                    cellImageView.opacity = 0.6
                    tileMap[Pair(row, col)] = grass


                    cellImageView.setOnMouseClicked {
                        handleTileClick(cellImageView, col, row)
                    }

                    add(cellImageView, col, row)
                }
            }
            alignment = javafx.geometry.Pos.CENTER

        }
        */

        gridpane {
            hgap = 1.0
            vgap = 1.0
            paddingAll = 0.0

            repeat(rowCount) { row ->
                repeat(columnCount) { col ->
                    val tile = Tile(row, col, tileSize, tileSize)
                    //val cellImageView = ImageView()
                    //cellImageView.
                    val tileRectangle = rectangle(tile.width, tile.height) {
                        stroke = Color.BLACK
                        fill = Color.TRANSPARENT

                    }
                    add(tileRectangle, col, row)
                }
            }
            alignment = javafx.geometry.Pos.CENTER
        }

    }

    private fun handleTileClick(cellImageView: ImageView, col: Int, row: Int) {

    }
}
