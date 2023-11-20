package model

import tornadofx.*
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ItemViewModel

enum class MobType {
    Fly, Walk
}

class Mob (cost: Int? = null, damage: Int? = null, health: Int? = null, type: MobType? = null){
    val costProperty = SimpleIntegerProperty(this, "cost", cost!!)
    var cost by costProperty

    val damageProperty = SimpleIntegerProperty(this, "cost", damage!!)
    var damage by damageProperty


    val healthProperty = SimpleIntegerProperty(this, "cost", health!!)
    var health by healthProperty

    val typeProperty = SimpleObjectProperty(this, "type", type!!)
    var type: MobType by typeProperty

}


class MobModel : ItemViewModel<Mob>() {
    val cost = bind(Mob::cost)
    val damage = bind(Mob::damage)
    val health = bind(Mob::health)
    val type = bind(Mob::type)
}