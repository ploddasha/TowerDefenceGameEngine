package model.fromEditing

import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import kotlinx.serialization.Serializable
import tornadofx.ItemViewModel

// It's not from Editing!!!

@Serializable
enum class TowerType {
    Fly, Walk, Both
}


class Tower (
    cost: Int? = null,
    damage: Int? = null,
    health: Int? = null,
    speed: Int? = null,
    attackRange: Int? = null,
){
    val costProperty = SimpleIntegerProperty(this, "cost", cost!!)
    var cost by costProperty

    val damageProperty = SimpleIntegerProperty(this, "damage", damage!!)
    var damage by damageProperty

    val healthProperty = SimpleIntegerProperty(this, "health", health!!)
    var health by healthProperty

    val speedProperty = SimpleIntegerProperty(this, "speed", speed!!)
    var speed by speedProperty

    val attackRangeProperty = SimpleIntegerProperty(this, "attackRange", 0)
    var attackRange by attackRangeProperty
}


class TowerModel : ItemViewModel<Tower>() {
    var cost = bind(Tower::cost)
    val damage = bind(Tower::damage)
    val health = bind(Tower::health)
    val speed = bind(Tower::speed)
    val attackRange = bind(Tower::attackRange)
}
