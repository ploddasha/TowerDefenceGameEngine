package viewModel.real

import model.fromEditing.MobType
import kotlin.math.pow

data class RealTower(
    var id: Int,
    var row: Int,
    var col: Int,
    var type: TowerType,
    var health: Int,
    var speed: Int,
    var damage: Int,
    var attackRange: Int
) {
    fun isInRange(mob: RealMob): Boolean {
        val distance = Math.sqrt((row - mob.row).toDouble().pow(2) + (col - mob.col).toDouble().pow(2))
        return distance <= attackRange
    }

    fun attackMob(mob: RealMob) {
        if (isInRange(mob)) {
            println("Моб рядом с башней, атакуем")
            mob.takeHealth(damage)
        }
    }

}

enum class TowerType{
    GroundTower,
    FlyingTower
}