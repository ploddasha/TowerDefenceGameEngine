package model.towers

import tornadofx.*
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ItemViewModel

enum class TowerType {
    Fly, Walk
}

class Tower (cost: Int? = null, damage: Int? = null, health: Int? = null, type: TowerType? = null, range: Int? = null){
    val costProperty = SimpleIntegerProperty(this, "cost", cost!!)
    var cost by costProperty

    val damageProperty = SimpleIntegerProperty(this, "cost", damage!!)
    var damage by damageProperty


    val healthProperty = SimpleIntegerProperty(this, "cost", health!!)
    var health by healthProperty

    val typeProperty = SimpleObjectProperty(this, "type", type!!)
    var type: TowerType by typeProperty

    val rangeProperty = SimpleIntegerProperty(this, "range", range!!)
    var range by rangeProperty
}


class TowerModel : ItemViewModel<Tower>() {
    val cost = bind(Tower::cost)
    val damage = bind(Tower::damage)
    val health = bind(Tower::health)
    val type = bind(Tower::type)
    val range = bind(Tower::range)
}