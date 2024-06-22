package viewModel

import client.NetworkClient
import javafx.application.Platform
import javafx.scene.control.Alert
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.CityModel
import tornadofx.Controller
import tornadofx.alert
import view.EnemyMapView


class EnemyGameController(
    private val moneyController: MoneyController,
    private val cityController: CityController,
    private val ratingController: RatingController,
    private val cityModel: CityModel,
    private val victoryController: VictoryController
) : Controller() {

    private var mapView: EnemyMapView? = null
    fun setMapView(mapView: EnemyMapView) {
        this.mapView = mapView
    }

    private val networkClient = NetworkClient()
    private var gameId = 1

    init {
        gameId = 1
    }
    private var isGameOn = true


    fun startPeriodicGameStateUpdates() {
        isGameOn = true

        GlobalScope.launch {
            while (isGameOn) {
                receiveGameState()
                delay(200)
            }
            receiveGameState()
            val gameState = networkClient.getOpponentState()
            if (gameState != null) {
                victoryController.setEnemyGameOver(true)
                victoryController.setEnemyCityHealth(gameState.cityHealth)
                victoryController.setEnemyRating(gameState.rating)
                val result = victoryController.check()
                if (result != "nothing") {
                    when (result) {
                        "victory" -> {
                            alert(Alert.AlertType.INFORMATION, "Congratulations!", "You won!")
                        }
                        "lose" -> {
                            alert(Alert.AlertType.INFORMATION, "Ooops...", "You lost!")
                        }
                        else -> {
                            alert(Alert.AlertType.INFORMATION, "Hmmmm...", "It's a draw!")
                        }
                    }
                }
            }
        }
    }


    fun receiveGameState() {
        GlobalScope.launch {
            val gameState = networkClient.getOpponentState()

            if (gameState != null) {
                println("Received game state: $gameState")

                Platform.runLater {
                    isGameOn = gameState.isGameOn

                    moneyController.setMoney(gameState.moneyAmount)
                    cityController.setHealth(gameState.cityHealth)
                    ratingController.setRating(gameState.rating)

                    // TODO clear old?
                    for (mob in gameState.mobs) {
                        mapView?.addMob(mob)
                    }
                    for (tower in gameState.towers) {
                        mapView?.addTower(tower)
                    }
                }
            } else {
                println("Failed to receive game state")
            }

        }
    }




}