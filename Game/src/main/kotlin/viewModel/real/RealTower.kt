package viewModel.real

import kotlinx.serialization.Serializable
import model.fromEditing.MobType
import kotlin.math.pow

@Serializable
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

    private fun matchTowerMob(tower: TowerType, mob: MobType): Boolean {
        return when (tower) {
            TowerType.BothTower -> true
            TowerType.FlyingTower -> mob == MobType.Fly || mob == MobType.Both
            TowerType.GroundTower -> mob == MobType.Walk || mob == MobType.Both
        }
    }

    fun attackMob(mob: RealMob) {
        if (!matchTowerMob(type, mob.type)) {
            return
        }
        if (isInRange(mob)) {
            println("Mob ${mob.id} is next to tower, attack")
            mob.takeHealth(damage)
        }
    }
}

enum class TowerType{
    GroundTower,
    FlyingTower,
    BothTower
}