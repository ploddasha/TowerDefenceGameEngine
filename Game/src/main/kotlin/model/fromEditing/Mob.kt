package model.fromEditing

import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import kotlinx.serialization.Serializable
import tornadofx.ItemViewModel

@Serializable
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
    type: MobType? = null
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
}


class MobModel : ItemViewModel<Mob>() {
    var cost = bind(Mob::cost)
    val damage = bind(Mob::damage)
    val health = bind(Mob::health)
    val speed = bind(Mob::speed)
    val type = bind(Mob::type)
    //val canAttack = bind(Mob::canAttack)
    val attackRange = bind(Mob::attackRange)
}
