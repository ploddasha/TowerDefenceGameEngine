package viewModel.real

import model.fromEditing.MobType

data class RealMob(
    var id: Int,
    var row: Int,
    var col: Int,
    var type: MobType,
    var health: Int,
    var speed: Int,
    var damage: Int,
    var attackRange: Int,
    var value: Int

) {

    fun move(drow: Int, dcol: Int) {
        row = (row + drow).coerceIn(0, 9)
        col = (col + dcol).coerceIn(0, 9)
    }

    fun takeHealth(damage: Int) {
        health -= damage
    }

}

