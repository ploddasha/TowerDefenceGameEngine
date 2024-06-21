package viewModel

import app.loadFiles.WaveConfig
import app.loadFiles.createMobModel
import client.NetworkClient
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import javafx.scene.control.Alert
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.CityModel
import model.data.GameState
import model.data.InitConfig
import model.fromEditing.MobsModel
import model.tower.Tower
import tornadofx.Controller
import tornadofx.alert
import tornadofx.runLater
import view.EnemyMapView
import view.MapView
import viewModel.real.RealMob
import viewModel.real.RealTower
import viewModel.real.TowerType
import viewModel.towerControllers.Fly
import viewModel.towerControllers.Walk
import viewModel.towerControllers.parseWalk
import viewModel.towerControllers.parseFly
import java.io.File
import java.util.*


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