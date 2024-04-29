package viewModel

import model.fromEditing.MobType
import model.fromEditing.MobsModel
import model.fromEditing.TileType
import model.tower.Tower
import tornadofx.Controller
import tornadofx.View
import tornadofx.runLater
import view.MapView
import viewModel.real.RealMob
import viewModel.real.RealTower
import viewModel.real.TowerType
import java.util.*


class GameController(
    //private val mapView: MapView
) : Controller() {

    var mapView: MapView? = null

    val mobsModel: MobsModel by inject()
    private var timer: Timer? = null
    private val mobs = mutableListOf<RealMob>()
    private val towers = mutableListOf<RealTower>()

    private var towerToPut: Tower? = null

    init {
        createMobModel(mobsModel)
        createRealMobs()
    }

    private fun createRealMobs() {
        for (i in 0 until mobsModel.mobsList.size) {
            val toCopy = mobsModel.mobsList[i];
            println("Здоровье моба: ${toCopy.health}")
            mobs.add(RealMob(id = i, row = 0, col = 0, type = toCopy.type, health = toCopy.health, speed = toCopy.speed, damage = toCopy.damage, attackRange = toCopy.attackRange))
        }
    }


    fun startGame() {
        timer = Timer()

        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                moveMob()
                fireTowers()
            }
        }, 0, 1000) // Запуск каждую секунду
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

    private fun moveMob() {
        val mob = mobs[0]
        if (mob.col >= 9 || mob.row >= 9) {
            timer?.cancel()
            println("Game Over")
            return
        }
        if (mob.health <= 0) {
            mobs.remove(mob)
            runLater {
                mapView?.deleteMobFromMap(mob.row, mob.col)
            }
            timer?.cancel()
            println("Умер")
            return
        }
        runLater {
            mapView?.add(mob)
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
        towers.add(RealTower(row * 20 + col, row, col, TowerType.FlyingTower, 100, 100, 50, 2))
    }

    fun setMyMapView(mapView: MapView) {
        this.mapView = mapView
    }
}