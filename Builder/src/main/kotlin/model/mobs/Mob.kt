package model

import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ItemViewModel

enum class MobType {
    Fly, Walk
}

class Mob (
    cost: Int? = null,
    damage: Int? = null,
    health: Int? = null,
    speed: Int? = null,
    //canAttack: Boolean? = null,
    attackRange: Int? = null,
    type: MobType? = null,
    fileName: String? = null
){
    val costProperty = SimpleIntegerProperty(this, "cost", cost!!)
    var cost by costProperty

    val damageProperty = SimpleIntegerProperty(this, "damage", damage!!)
    var damage by damageProperty

    val healthProperty = SimpleIntegerProperty(this, "health", health!!)
    var health by healthProperty

    val speedProperty = SimpleIntegerProperty(this, "speed", speed!!)
    var speed by speedProperty

    val typeProperty = SimpleObjectProperty(this, "type", type!!)
    var type: MobType by typeProperty

    //val canAttackProperty = SimpleBooleanProperty(this, "canAttack", false)
    //var canAttack by canAttackProperty

    val attackRangeProperty = SimpleIntegerProperty(this, "attackRange", 0)
    var attackRange by attackRangeProperty

    val fileNameProperty = SimpleObjectProperty(this, "fileName", fileName!!)
    var fileName: String by fileNameProperty
}


class MobModel : ItemViewModel<Mob>() {
    val cost = bind(Mob::cost)
    val damage = bind(Mob::damage)
    val health = bind(Mob::health)
    val speed = bind(Mob::speed)
    val type = bind(Mob::type)
    //val canAttack = bind(Mob::canAttack)
    val attackRange = bind(Mob::attackRange)
    val fileName = bind(Mob::fileName)
}
