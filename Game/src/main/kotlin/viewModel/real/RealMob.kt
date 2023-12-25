package viewModel.real

import javafx.application.Platform
import tornadofx.Controller
import tornadofx.Stylesheet.Companion.root
import view.GameView
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

data class RealMob(
    var x: Int,
    var y: Int
) {
    fun move(dx: Int, dy: Int) {
        x = (x + dx).coerceIn(0, 9)
        y = (y + dy).coerceIn(0, 9)
    }
}


class GameController : Controller() {

    private val executor = Executors.newScheduledThreadPool(1)

    private val mob = RealMob(0, 0)

    init {
        // Инициализация вашего представления или других данных, если это необходимо
        // ...
    }

    // Запуск потока для движения мобов каждую секунду
    fun startMobMovement() {
        executor.scheduleAtFixedRate({
            Platform.runLater {
                moveMobs(1, 0) // Например, мобы двигаются вправо каждую секунду
            }
        }, 0, 1, TimeUnit.SECONDS)
    }

    fun moveMobs(dx: Int, dy: Int) {
        // Logic for moving the mob
        val newX = (mob.x + dx).coerceIn(0, 9)  // Assuming 10x10 grid, so the upper limit should be 9
        val newY = (mob.y + dy).coerceIn(0, 9)
        mob.x = newX
        mob.y = newY

        // Update the view
        //Platform.runLater {
        //    root.clear()
        //    root.add(find<GameView>().root)
        //}
    }

    fun stop() {
        executor.shutdown()
    }
}
