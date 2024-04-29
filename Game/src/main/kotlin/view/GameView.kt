package view

import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import model.CityModel
import model.fromEditing.TileType
import tornadofx.*
import viewModel.GameController
import viewModel.MoneyController
import viewModel.towerControllers.FlyingTowerController
import viewModel.towerControllers.GroundTowerController


class GameView : View("Bashenki") {
    val gameController = GameController()

    val mapView = MapView(gameController)

    //val gameController = GameController(mapView)

    val moneyController = MoneyController()
    val groundTowerController = GroundTowerController()
    val flyingTowerController = FlyingTowerController()

    val cityModel = CityModel()

    val shopView = ShopView(gameController, moneyController, groundTowerController, flyingTowerController, cityModel)


    init {
    }
    data class GameTile(val x: Int, val y: Int, val width: Double, val height: Double, val tileType: TileType)


    /*
       private val pauseMenuView = PauseMenuView()

       private val musicController: MusicController  by inject()

       init {
           musicController.playMusic("D:/ggwp/TowerDefenceGameEngine/Game/src/main/resources/music/game_music.mp3")
       } */


    override val root = stackpane {

        addClass("game-stack-pane")

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
    }


}
