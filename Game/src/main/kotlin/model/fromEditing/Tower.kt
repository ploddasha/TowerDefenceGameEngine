package model.fromEditing

import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*
import javafx.beans.property.SimpleObjectProperty
import kotlinx.serialization.Serializable
import tornadofx.ItemViewModel

@Serializable
enum class TowerType {
    Fly, Walk, Both
}

class Tower (
    name: String? = null,
    fileName: String? = null,
    cost: Int? = null,
    damage: Int? = null,
    health: Int? = null,
    type: TowerType? = null,
    range: Int? = null
){
    val nameProperty = SimpleObjectProperty(this, "name", name!!)
    var name: String by nameProperty

    val fileNameProperty = SimpleObjectProperty(this, "fileName", fileName!!)
    var fileName: String by fileNameProperty

    val costProperty = SimpleIntegerProperty(this, "cost", cost!!)
    var cost by costProperty

    val damageProperty = SimpleIntegerProperty(this, "damage", damage!!)
    var damage by damageProperty


    val healthProperty = SimpleIntegerProperty(this, "health", health!!)
    var health by healthProperty

    val rangeProperty = SimpleIntegerProperty(this, "range", range!!)
    var range by rangeProperty

    val typeProperty = SimpleObjectProperty(this, "type", type!!)
    var type: TowerType by typeProperty
}

class TowerModel : ItemViewModel<Tower>() {
    val name = bind(Tower::name)
    val fileName = bind(Tower::fileName)
    val cost = bind(Tower::cost)
    val damage = bind(Tower::damage)
    val health = bind(Tower::health)
    val range = bind(Tower::range)
    val type = bind(Tower::type)
}