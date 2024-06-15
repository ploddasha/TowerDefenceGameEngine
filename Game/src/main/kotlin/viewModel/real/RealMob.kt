package viewModel.real

import kotlinx.serialization.Serializable
import model.fromEditing.MobType
import model.TilePair

@Serializable
data class RealMob(
    var id: Int,
    var row: Int,
    var col: Int,
    var type: MobType,
    var health: Int,
    var speed: Int,
    var damage: Int,
    var attackRange: Int,
    var value: Int,
) {

    val mobPredPositions = mutableMapOf<Int, Pair<Int, Int>>()
    val visited: MutableSet<TilePair> = mutableSetOf()

    fun move(drow: Int, dcol: Int) {
        row = (row + drow).coerceIn(0, 9)
        col = (col + dcol).coerceIn(0, 9)
    }

    fun takeHealth(damage: Int) {
        health -= damage
    }

    fun moveTo(first: Int, second: Int) {
        row = first
        col = second
    }

}

