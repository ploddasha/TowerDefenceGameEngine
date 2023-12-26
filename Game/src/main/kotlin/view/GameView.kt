package view

import app.loadFiles.createMapModel
import app.loadFiles.createMobModel
import app.loadFiles.createTowersModel
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import model.fromEditing.*
import tornadofx.*
import viewModel.real.GameController
import viewModel.real.RealMob
import viewModel.MusicController


class GameView : View("Bashenki") {

    private val musicController: MusicController  by inject()
    init {
        //musicController.playMusic("D:/ggwp/TowerDefenceGameEngine/Game/src/main/resources/music/game_music.mp3")

    }

    val mobsModel: MobsModel by inject()
    val mapModel: MapModel by inject()
    val towersModel: TowersModel by inject()


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
    private val numRows = 20
    private val numCols = 20

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
            createTowersModel(towersModel)
            label(text = towersModel.towersList[0].type.toString())




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

                    cellImageView.setOnMouseClicked {
                        handleTileClick(cellImageView, col, row)
                    }

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

            alignment = javafx.geometry.Pos.CENTER

        }
    }

    private fun handleTileClick(cellImageView: ImageView, col: Int, row: Int) {
        var purchasedTower = towersModel.selectedTower
        //cellImageView.image = Image(javaClass.getResourceAsStream(purchasedTower.fileName))
        //println("Tile changed at position ($col, $row) to $purchasedTower")

        if (purchasedTower != null) {
            cellImageView.image = Image(javaClass.getResourceAsStream(purchasedTower.fileName!!))
            println("Tower changed at position ($col, $row) to ${purchasedTower.fileName}")
        }
    }


}
