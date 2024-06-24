package viewModel

import client.NetworkClient
import javafx.application.Platform
import javafx.scene.control.Alert
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.CityModel
import model.data.GameState
import tornadofx.Controller
import tornadofx.alert
import tornadofx.runLater
import view.EnemyMapView
import view.SaveRatingView


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


    @OptIn(DelicateCoroutinesApi::class)
    fun startPeriodicGameStateUpdates() {
        isGameOn = true

        GlobalScope.launch {
            while (!victoryController.getFinished()) {
                receiveGameState()
                delay(400)
            }
            receiveGameState()

        }
    }

    private var previousGameState: GameState? = null



    @OptIn(DelicateCoroutinesApi::class)
    private fun receiveGameState() {
        GlobalScope.launch {
            val gameState = networkClient.getOpponentState()

            if (gameState != null) {
                println("Received game state: $gameState")

                Platform.runLater {

                    isGameOn = gameState.isGameOn

                    moneyController.setMoney(gameState.moneyAmount)
                    cityController.setHealth(gameState.cityHealth)
                    ratingController.setRating(gameState.rating)

                    previousGameState?.let { prevGameState ->
                        val currentMobIds = gameState.mobs.map { it.id }.toSet()
                        prevGameState.mobs.forEach { mob ->
                            if (mob.id !in currentMobIds) {
                                mapView?.deleteMobFromMap(mob.row, mob.col)
                            }
                        }
                    }

                    // TODO clear old?
                    for (mob in gameState.mobs) {
                        mapView?.addMob(mob)
                    }
                    for (tower in gameState.towers) {
                        mapView?.addTower(tower)
                    }

                    previousGameState = gameState

                }

                if (!gameState.isGameOn) {
                    victoryController.setEnemyGameOver(true)
                    victoryController.setEnemyCityHealth(gameState.cityHealth)
                    victoryController.setEnemyRating(gameState.rating)
                    val result = victoryController.check()
                    if (result != "nothing") {
                        when (result) {
                            "victory" -> showNameInputView("Congratulations! You won!")
                            "lose" -> showNameInputView("Ooops... You lost!")
                            else -> showNameInputView("Hmmmm... It's a draw!")
                        }
                    }
                }
            } else {
                println("Failed to receive game state")
            }

        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun showNameInputView(message: String) {
        val totalMoneySpent = moneyController.getMoneySpent()

        find<SaveRatingView>().apply {
            setResultMessage("$message\nTotal Money Spent: $totalMoneySpent")
            this.onSave = { name ->
                GlobalScope.launch {
                    networkClient.addToRating(
                        name = "Cool",
                        result = ratingController.getRating(),
                        player = name
                    )
                }
                find<SaveRatingView>().apply {
                    setResultMessage("$message\nTotal Money Spent: $totalMoneySpent\nRating saved for $name.")
                }
            }

            openModal(block = true)
        }
    }



}