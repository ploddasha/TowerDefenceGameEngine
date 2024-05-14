package viewModel

import app.loadFiles.createMobModel
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableValue
import model.CityModel
import model.fromEditing.MobType
import model.fromEditing.MobsModel
import model.tower.Tower
import tornadofx.Controller
import tornadofx.runLater
import view.MapView
import viewModel.real.RealMob
import viewModel.real.RealTower
import viewModel.real.TowerType
import java.util.*


class GameController(
    private val moneyController: MoneyController,
    private val cityController: CityController,
    private val cityModel: CityModel
) : Controller() {

    private var mapView: MapView? = null

    private val mobsModel: MobsModel by inject()
    private var timer: Timer? = null
    private var waves: MutableList<MutableList<RealMob>> = mutableListOf<MutableList<RealMob>>()
    private val mobs = mutableListOf<RealMob>()
    private val towers = mutableListOf<RealTower>()

    private var towerToPut: Tower? = null

    private var start: Pair<Int, Int> = Pair(0, 0)
    private var city: Pair<Int, Int> = Pair(9, 9)


    init {
        createMobModel(mobsModel)
        createRealMobs()
        createWaves()
    }

    private fun createWaves() {
        val mobsFirstWave = mutableListOf<RealMob>()
        mobsFirstWave.add(RealMob(10, 0, 0, MobType.Walk, 100, 100, 2, 0, 50))
        mobsFirstWave.add(RealMob(10, 0, 0, MobType.Walk, 100, 100, 2, 0, 60))
        waves.add(mobsFirstWave)
        val mobsSecondWave = mutableListOf<RealMob>()
        mobsSecondWave.add(RealMob(10, 0, 0, MobType.Fly, 300, 100, 3, 0, 100))
        mobsSecondWave.add(RealMob(10, 0, 0, MobType.Fly, 300, 100, 3, 0, 110))
        waves.add(mobsSecondWave)

    }

    private fun createRealMobs() {
        for (i in 0 until mobsModel.mobsList.size) {
            val toCopy = mobsModel.mobsList[i]
            mobs.add(RealMob(id = i, row = start.second, col = start.first, type = toCopy.type,
                health = toCopy.health, speed = toCopy.speed, damage = toCopy.damage,
                attackRange = toCopy.attackRange, value = toCopy.cost))
        }
    }


    fun startGame() {
        timer = Timer()

        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                moveMob(mobs[0])
                fireTowers()
            }
        }, 0, 500)
    }

    var currentWave = 0

    var timer2 = Timer()
    fun startGameWithWaves() {
        timer2.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                moveMobs()
            }
        }, 0, 500)

        currentWave++
    }

    var currentMobId = 0
    private fun moveMobs() {
        timer = Timer()

        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                moveMob(waves[currentWave][currentMobId])
                if (currentMobId  == waves[currentWave].size - 1) {
                    timer2.cancel()
                    return
                } else {
                    currentMobId++
                }
                fireTowers()
            }
        }, 0, 1000)
    }

    private fun moveMob(mob: RealMob) {
        if (mob.health <= 0) {
            mobs.remove(mob)
            runLater {
                moneyController.addMoney(mob.value)
                mapView?.deleteMobFromMap(mob.row, mob.col)
            }
            timer?.cancel()
            println("Умер")
            return
        }
        if (mob.row == city.first && mob.col == city.second) {
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
            timer?.cancel()
            println("Вторгся >:)")

            return
        }
        runLater {
            mapView?.add(mob)
        }
    }

    private fun fireTowers() {
        towers.forEach { tower ->
            mobs.forEach { mob ->
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
}