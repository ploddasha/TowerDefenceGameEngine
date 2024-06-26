package view

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import model.CityModel
import model.fromEditing.TileType
import tornadofx.*
import viewModel.*
import viewModel.towerControllers.FlyingTowerController
import viewModel.towerControllers.GroundTowerController
import client.NetworkClient


class GameView(
    gameName: String,
    rating: String
) : View("Bashenki") {

    private val moneyController = MoneyController()
    private val cityController = CityController()
    private val victoryController = VictoryController()

    private val groundTowerController = GroundTowerController()
    private val flyingTowerController = FlyingTowerController()
    private val cityModel = CityModel()
    private val ratingController = RatingController()
    private val waveController = WaveController()

    private val gameController = GameController(moneyController, cityController, ratingController, cityModel, false, victoryController, waveController)

    private val mapView = MapView(gameController)
    private val allGamesView: AllGamesView by inject()
    private val shopView = ShopView(gameController, moneyController, cityController,
        groundTowerController, flyingTowerController, cityModel, this)
    private val pauseMenuView = PauseMenuView(this)
    private val ratingMenuView = RatingMenuView(this, gameName, rating)

    private val moneyLabel = label {
        style {
            fontWeight = FontWeight.BOLD
        }
    }
    private val moneyBackground = stackpane {
        rectangle {
            width = 100.0
            height = 30.0
            fill = Color.WHITE
            arcWidth = 20.0
            arcHeight = 20.0
        }
        add(moneyLabel)
        alignment = Pos.CENTER
    }
    private val moneyIcon = ImageView(Image(resources.url("/configs/coin.png").toString()))
    private val heartIcon = ImageView(Image(resources.url("/configs/heart.png").toString()))
    private val starIcon = ImageView(Image(resources.url("/configs/star.png").toString()))
    private val waveIcon = ImageView(Image(resources.url("/configs/wave_icon.png").toString()))

    private val cityLabel = label() {
        style {
            fontWeight = FontWeight.BOLD
        }
    }
    private val cityBackground = stackpane {
        rectangle {
            width = 100.0
            height = 30.0
            fill = Color.WHITE
            arcWidth = 20.0
            arcHeight = 20.0
        }
        add(cityLabel)
        alignment = Pos.CENTER
    }


    private val ratingLabel = label() {
        style {
            fontWeight = FontWeight.BOLD
        }
    }
    val ratingBackground = stackpane {
        rectangle {
            width = 100.0
            height = 30.0
            fill = Color.WHITE
            arcWidth = 20.0
            arcHeight = 20.0
        }
        add(ratingLabel)
        alignment = Pos.CENTER
    }

    private val waveLabel = label() {
        style {
            fontWeight = FontWeight.BOLD
        }
    }
    val waveBackground = stackpane {
        rectangle {
            width = 100.0
            height = 30.0
            fill = Color.WHITE
            arcWidth = 20.0
            arcHeight = 20.0
        }
        add(waveLabel)
        alignment = Pos.CENTER
    }

    init {
        moneyIcon.fitWidth = 35.0
        moneyIcon.fitHeight = 35.0
        moneyLabel.textProperty().bind(moneyController.moneyAmountProperty().asString("Money: %d"))

        heartIcon.fitWidth = 30.0
        heartIcon.fitHeight = 30.0
        cityLabel.textProperty().bind(cityController.cityProperty().asString("City: %d"))

        starIcon.fitWidth = 30.0
        starIcon.fitHeight = 30.0
        ratingLabel.textProperty().bind(ratingController.ratingProperty().asString("Rating: %d"))

        waveIcon.fitWidth = 30.0
        waveIcon.fitHeight = 30.0
        waveLabel.textProperty().bind(waveController.currentWaveProperty().asString("Wave %d of ").concat(waveController.totalWavesProperty.asString("%d")))

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
        musicController.playMusic("./src/main/resources/music/game_music.mp3")
    } */


    override val root = borderpane {

        addClass("game-stack-pane")

        add(gameOverText)

        top {
            hbox {
                alignment = Pos.CENTER
                vbox {
                    alignment = Pos.CENTER
                    hbox {
                        alignment = Pos.CENTER
                        maxWidth = 800.0

                        addClass("game-top-background")

                        //paddingAll = 10.0
                        //spacing = 10.0

                        hbox {
                            alignment = Pos.CENTER
                            maxWidth = 850.0

                            hbox {
                                alignment = Pos.CENTER
                                paddingAll = 4.0
                                spacing = 30.0

                                button("Start Wave") {
                                    style {
                                        fontSize = 14.px
                                        padding = box(5.px, 30.px)
                                        paddingAll = 5.0
                                        backgroundColor += Color.BLUE
                                        textFill = Color.WHITE
                                        fontWeight = FontWeight.BOLD
                                    }
                                    vboxConstraints {
                                        marginRight = 10.0
                                    }
                                    action {
                                        startGame()
                                    }
                                }
                            }

                            hbox {
                                alignment = Pos.CENTER
                                paddingAll = 6.0
                                spacing = 10.0

                                button("Pause") {
                                    style {
                                        fontSize = 14.px
                                        padding = box(5.px, 12.px)
                                        backgroundColor += Color.rgb(255, 152, 0)
                                        textFill = Color.WHITE
                                        fontWeight = FontWeight.BOLD
                                    }
                                    vboxConstraints {
                                        marginRight = 10.0
                                    }
                                    action {
                                        replaceWith(pauseMenuView)
                                    }
                                }

                                button("Shop") {
                                    style {
                                        fontSize = 14.px
                                        padding = box(5.px, 12.px)
                                        paddingAll = 5.0
                                        backgroundColor += Color.GREEN
                                        textFill = Color.WHITE
                                        fontWeight = FontWeight.BOLD
                                    }
                                    vboxConstraints {
                                        marginRight = 10.0
                                    }
                                    action {
                                        replaceWith(shopView)
                                    }
                                }

                                button("Rating") {
                                    style {
                                        fontSize = 14.px
                                        padding = box(5.px, 12.px)
                                        paddingAll = 5.0
                                        backgroundColor += Color.RED
                                        textFill = Color.WHITE
                                        fontWeight = FontWeight.BOLD
                                    }
                                    vboxConstraints {
                                        marginRight = 10.0
                                    }
                                    action {
                                        replaceWith(ratingMenuView)
                                    }
                                }
                            }

                            hbox {
                                alignment = Pos.CENTER
                                paddingAll = 4.0
                                spacing = 30.0

                                button("Back to menu") {
                                    style {
                                        fontSize = 14.px
                                        padding = box(5.px, 20.px)
                                        paddingAll = 5.0
                                        backgroundColor += Color.BLACK
                                        textFill = Color.WHITE
                                        fontWeight = FontWeight.BOLD
                                    }
                                    vboxConstraints {
                                        marginRight = 10.0
                                    }
                                    action {
                                        replaceWith(allGamesView)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        center {
            useMaxWidth = true

            hbox{
                alignment = Pos.CENTER
                vbox {
                    alignment = Pos.CENTER
                    add(mapView)
                }
            }
        }

        bottom {
            hbox{
                addClass("game-bottom-background")
                alignment = Pos.BOTTOM_CENTER
                hbox{
                    spacing = 10.0

                    hbox {
                        hbox {
                            add(moneyIcon)
                            prefWidth = 35.0
                        }
                        add(moneyBackground)
                        alignment = Pos.TOP_RIGHT
                    }

                    hbox {
                        add(heartIcon)
                        add(cityBackground)
                        alignment = Pos.TOP_RIGHT
                    }

                    hbox {
                        add(starIcon)
                        add(ratingBackground)
                        alignment = Pos.TOP_RIGHT
                    }

                    hbox {
                        add(waveIcon)
                        add(waveBackground)
                        alignment = Pos.TOP_RIGHT
                    }
                }
            }

        }

    }

    private fun startGame() {
        gameController.setMyMapView(mapView)
        gameController.startGameWithWaves()
    }
}
