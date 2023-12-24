package model.towers

import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*
import javafx.beans.property.SimpleObjectProperty
import model.Mob
import model.MobType
import tornadofx.ItemViewModel

enum class TowerType {
    Fly, Walk
}

class Tower (name: String? = null, cost: Int? = null, damage: Int? = null, health: Int? = null, type: TowerType? = null){
    val nameProperty = SimpleObjectProperty(this, "name", name!!)
    var name: String by nameProperty

    val costProperty = SimpleIntegerProperty(this, "cost", cost!!)
    var cost by costProperty

    val damageProperty = SimpleIntegerProperty(this, "cost", damage!!)
    var damage by damageProperty


    val healthProperty = SimpleIntegerProperty(this, "cost", health!!)
    var health by healthProperty

    val typeProperty = SimpleObjectProperty(this, "type", type!!)
    var type: TowerType by typeProperty
}

class TowerModel : ItemViewModel<Tower>() {
    val name = bind(Tower::name)
    val cost = bind(Tower::cost)
    val damage = bind(Tower::damage)
    val health = bind(Tower::health)
    val type = bind(Tower::type)
}