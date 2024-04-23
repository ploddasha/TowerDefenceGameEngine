package viewModel

import model.fromEditing.MobType
import model.fromEditing.MobsModel
import model.fromEditing.TileType
import tornadofx.Controller
import tornadofx.runLater
import view.MapView
import viewModel.real.RealMob
import java.util.*


class GameController(
    private val mapView: MapView
) : Controller() {
    val mobsModel: MobsModel by inject()
    private var timer: Timer? = null
    private val mobs = mutableListOf<RealMob>()

    init {
        createMobModel(mobsModel)
        createRealMobs()
    }

    private fun createRealMobs() {
        for (i in 0 until mobsModel.mobsList.size) {
            val toCopy = mobsModel.mobsList[i];
            mobs.add(RealMob(i, 0, 0, toCopy.type, toCopy.health, toCopy.speed, toCopy.damage, toCopy.attackRange))
        }
    }



    fun startGame() {
        timer = Timer()

        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                moveMob()
            }
        }, 0, 1000) // Запуск каждую секунду
    }

    private fun moveMob() {
        val mob = mobs[0]
        if (mob.col >= 9 || mob.row >= 9) {
            timer?.cancel()
            println("Game Over")
            return
        }

        //mob.move(0, 1)

        runLater {
            mapView.add(mob)
        }
    }
}