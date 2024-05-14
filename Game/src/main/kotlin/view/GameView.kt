package view

import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import model.CityModel
import model.fromEditing.TileType
import tornadofx.*
import viewModel.CityController
import viewModel.GameController
import viewModel.MoneyController
import viewModel.towerControllers.FlyingTowerController
import viewModel.towerControllers.GroundTowerController


class GameView : View("Bashenki") {

    val moneyController = MoneyController()
    val cityController = CityController()

    val groundTowerController = GroundTowerController()
    val flyingTowerController = FlyingTowerController()
    val cityModel = CityModel()

    val gameController = GameController(moneyController, cityController, cityModel)

    val mapView = MapView(gameController)

    //val gameController = GameController(mapView)



    val shopView = ShopView(gameController, moneyController, cityController, groundTowerController, flyingTowerController, cityModel)

    val moneyLabel = label()
    val moneyBackground = stackpane {
        rectangle {
            width = 100.0
            height = 30.0
            fill = Color.WHITE
        }
        add(moneyLabel)
        alignment = Pos.CENTER_RIGHT
    }
    val moneyIcon = ImageView(Image(resources.url("/configs/coin.jpg").toString()))


    val cityLabel = label()
    val cityBackground = stackpane {
        rectangle {
            width = 100.0
            height = 30.0
            fill = Color.WHITE
        }
        add(cityLabel)
        alignment = Pos.CENTER_RIGHT
    }


    init {
        moneyLabel.textProperty().bind(moneyController.moneyAmountProperty().asString("Money: %d"))
        moneyIcon.fitWidth = 35.0
        moneyIcon.fitHeight = 35.0

        cityLabel.textProperty().bind(cityController.cityProperty().asString("City: %d"))

    }
    data class GameTile(val x: Int, val y: Int, val width: Double, val height: Double, val tileType: TileType)


    val gameOverText = text("Game Over") {
        style {
            fontSize = 48.px
            fill = Color.RED
            fontWeight = FontWeight.BOLD
        }
        visibleProperty().bind(gameController.gameOverProperty()) // Привязываем видимость к свойству gameOver
    }

    /*
       private val pauseMenuView = PauseMenuView()

       private val musicController: MusicController  by inject()

       init {
           musicController.playMusic("D:/ggwp/TowerDefenceGameEngine/Game/src/main/resources/music/game_music.mp3")
       } */


    override val root = stackpane {

        addClass("game-stack-pane")

        add(gameOverText)


        vbox {

            paddingTop = 10.0
            paddingRight = 7.0
            alignment = Pos.TOP_RIGHT

            button("Start Game") {
                style {
                    fontSize = 14.px
                    padding = box(5.px, 10.px)
                    paddingAll = 5.0
                    backgroundColor += Color.BLUE
                    textFill = Color.WHITE
                    fontWeight = FontWeight.BOLD
                }
                vboxConstraints {
                    marginBottom = 10.0
                }
                action {
                    startGame()
                }
            }




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

            hbox(){
                add(moneyBackground)
                add(moneyIcon)
                alignment = Pos.TOP_RIGHT
            }
            add(cityBackground)


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
                    replaceWith(shopView)
                    //replaceWith(ShopView::class)
                }
            }
            add(mapView)
        }
    }

    private fun startGame() {
        gameController.setMyMapView(mapView)
        gameController.startGame()
        //gameController.startGameWithWaves()
    }


}
