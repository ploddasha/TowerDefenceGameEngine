package viewModel

import app.loadFiles.WaveConfig
import app.loadFiles.createMobModel
import client.NetworkClient
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


class GameController(
    private val moneyController: MoneyController,
    private val cityController: CityController,
    private val ratingController: RatingController,
    private val cityModel: CityModel,
    private val doSendGameState: Boolean,
) : Controller() {

    private var mapView: MapView? = null

    private val mobsModel: MobsModel by inject()
    private var timer: Timer? = null
    private var waves: MutableList<MutableList<RealMob>> = mutableListOf()
    private val mobs = mutableListOf<RealMob>()
    private val towers = mutableListOf<RealTower>()

    private var towerToPut: Tower? = null

    private var start: Pair<Int, Int> = Pair(0, 0)
    private var city: Pair<Int, Int> = Pair(9, 9)

    private var walkList = mutableListOf<Walk>()
    private var flyList = mutableListOf<Fly>()

    private val networkClient = NetworkClient()
    private var gameId = 1

    private var currentWave = 0


    init {
        gameId = 1
        loadInitJson()
        createMobModel(mobsModel, "./src/main/resources/configs/games/game$gameId/MobsData.json")
        createRealMobs()
        loadWavesFromFile()

        runAsync {
            walkList = parseWalk()
            flyList = parseFly()
        }
    }


    private fun loadInitJson() {
        val jsonString = File("./src/main/resources/configs/games/game$gameId/InitData.json").readText()
        val gameConfig = Json.decodeFromString<InitConfig>(jsonString)

        cityController.addCityHealth(gameConfig.cityHealth)
        moneyController.addMoney(gameConfig.initialMoney)
    }

    private fun loadWavesFromFile() {
        val jsonString = File("./src/main/resources/configs/games/game$gameId/WavesData.json").readText()
        val waveConfig = Json.decodeFromString<WaveConfig>(jsonString)
        waves = waveConfig.waves.map { wave ->
            wave.flatMap { mobConfig ->
                val templateMob = mobsModel.mobsList.find { it.name == mobConfig.name }
                    ?: throw IllegalArgumentException("No mob found with name: ${mobConfig.name}")
                List(mobConfig.amount) {
                    RealMob(
                        id = UUID.randomUUID().mostSignificantBits.toInt(), // Unique ID for each mob
                        row = start.second,
                        col = start.first,
                        type = templateMob.type,
                        health = templateMob.health,
                        speed = templateMob.speed,
                        damage = templateMob.damage,
                        attackRange = templateMob.attackRange,
                        value = templateMob.cost,
                        name = templateMob.name
                    )
                }
            }.toMutableList()
        }.toMutableList()
    }


    private fun createRealMobs() {
        for (i in 0 until mobsModel.mobsList.size) {
            val toCopy = mobsModel.mobsList[i]
            mobs.add(
                RealMob(
                    id = i,
                    row = start.second,
                    col = start.first,
                    type = toCopy.type,
                    health = toCopy.health,
                    speed = toCopy.speed,
                    damage = toCopy.damage,
                    attackRange = toCopy.attackRange,
                    value = toCopy.cost,
                    name = toCopy.name
                )
            )
        }
    }


    fun startPeriodicGameStateUpdates() {
        val gameOver = false

        if (doSendGameState) {
            GlobalScope.launch {
                while (!gameOver) {
                    sendGameState()
                    delay(500)
                }
            }
            sendGameState()
        }
    }

    fun sendGameState() {
        GlobalScope.launch {
            val gameState = GameState(
                isGameOn = !gameOverProperty().value,
                moneyAmount = moneyController.getCurrentMoneyAmount(),
                cityHealth = cityModel.getHealth(),
                rating = ratingController.getRating(),
                currentWave = currentWave,
                mobs = waves[currentWave],
                towers = towers.toList()
            )
            networkClient.sendGameState(gameState)
        }
    }


    fun startGameWithWaves() {
        println("START WAVE")
        //startPeriodicGameStateUpdates() For competitive

        runLater {
            GlobalScope.launch {
                if (currentWave < waves.size) {
                    val wave = waves[currentWave]
                    val jobs = wave.map { mob ->
                        launch {
                            var alive = true
                            while (alive && mob.health > 0 && !mobReachedCity(mob)) {

                                moveMob(mob)
                                runLater {
                                    fireTowers() // вызываем fireTowers после каждого перемещения моба
                                }
                                delay(500) // задержка в 0.5 секунды между движениями моба

                                if (mob.health <= 0) {
                                    alive = false
                                    handleMobDeath(mob)
                                }
                            }
                            if (alive && mob.health > 0) { // Если моб достиг города
                                handleMobReachedCity(mob)
                            }
                        }
                    }
                    jobs.joinAll() // Ждем завершения всех корутин

                    currentWave++
                    checkVictory()
                } else {
                    checkVictory()
                }
            }
        }
    }


    private fun checkVictory() {
        if (currentWave >= waves.size) {
            runLater {
                if (cityController.getCityHealth() > 0) {
                    println("You won! Your rating is: ${ratingController.getRating()}")
                    alert(Alert.AlertType.INFORMATION, "Congratulations!", "You won! Your rating is: ${ratingController.getRating()}")
                } else {
                    println("You lost! Your rating is: ${ratingController.getRating()}")
                    alert(Alert.AlertType.INFORMATION, "Ha-ha-ha!", "You lost! Your rating is: ${ratingController.getRating()}")
                }
                setGameOver(true)
            }
        }
    }


    private fun mobReachedCity(mob: RealMob): Boolean {
        return mob.row == city.first && mob.col == city.second
    }

    private fun handleMobReachedCity(mob: RealMob) {
        mobs.remove(mob)
        runLater {
            cityModel.setHealth(cityModel.getHealth() - mob.damage)
            cityController.subtractCityHealth(mob.damage)

            mapView?.deleteMobFromMap(mob.row, mob.col)

            if (cityController.getCityHealth() == 0) {
                setGameOver(true)
                println("Game Over")
            }
        }
    }

    private fun handleMobDeath(mob: RealMob) {
        runLater {
            mobs.remove(mob)
            moneyController.addMoney(mob.value)
            mapView?.deleteMobFromMap(mob.row, mob.col)
            ratingController.saveScore(mob.value)
        }
        println("Умер")
    }

    private fun moveMob(mob: RealMob) {
        if (mob.health <= 0) {
            handleMobDeath(mob)
            return
        }
        if (mobReachedCity(mob)) {
            handleMobReachedCity(mob)
            return
        }
        runLater {
            mapView?.add(mob)
        }
    }


    private fun fireTowers() {
        towers.forEach { tower ->
            waves[currentWave].forEach { mob ->
                if (tower.isInRange(mob)) {
                    tower.attackMob(mob)
                }
            }
        }
    }

    fun setTowerToPut(tower: Tower) {
        println("Покупаем башню")
        this.towerToPut = tower
    }

    fun removeTowerToPut() {
        this.towerToPut = null
    }

    fun getTowerToPut(): Tower? {
        return towerToPut
    }

    fun createRealTower(tower: Tower, col: Int, row: Int) {
        //TODO
        towers.add(RealTower(row * 20 + col, row, col, TowerType.FlyingTower, 100, 100, 50, 2))
    }

    fun setMyMapView(mapView: MapView) {
        this.mapView = mapView
    }

    fun addStart(x: Int, y: Int) {
        this.start = Pair(x, y)
    }

    fun addCity(x: Int, y: Int) {
        this.city = Pair(x, y)
    }

    private val _gameOver = SimpleBooleanProperty(false)

    fun gameOverProperty(): ObservableValue<out Boolean> {
        return _gameOver
    }

    private fun setGameOver(value: Boolean) {
        _gameOver.set(value)
    }

    fun stopGame() {
        TODO("Not yet implemented")
    }


}