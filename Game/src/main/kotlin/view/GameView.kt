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
import viewModel.MusicController


class GameView : View("Bashenki") {

    private val musicController: MusicController  by inject()
    init {
        musicController.playMusic("D:/game2/TowerDefenceGameEngine22222/Game/src/main/resources/music/game_music.mp3")

    }

    val mobsModel: MobsModel by inject()
    val mapModel: MapModel by inject()

    val gameController: GameController by inject()


    private val tileMap = mutableMapOf<Pair<Int, Int>, String>()


    private val pauseMenuView = PauseMenuView()

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






            button("Pause") {
                style {
                    fontSize = 14.px
                    padding = box(5.px, 10.px)
                    backgroundColor += Color.rgb(255, 152, 0)
                    textFill = Color.WHITE
                    fontWeight = FontWeight.BOLD
                }
                vboxConstraints {
                    marginBottom = 10.0
                }
                action {
                    //pauseMenuView.root.isVisible = true
                    replaceWith(PauseMenuView::class)
                }
            }

            button("Shop") {
                style {
                    fontSize = 14.px
                    padding = box(5.px, 10.px)
                    paddingAll = 5.0
                    backgroundColor += Color.GREEN
                    textFill = Color.WHITE
                    fontWeight = FontWeight.BOLD
                }
                vboxConstraints {
                    marginBottom = 10.0
                }
                action {
                    replaceWith(ShopView::class)
                }
            }
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
    }


}
