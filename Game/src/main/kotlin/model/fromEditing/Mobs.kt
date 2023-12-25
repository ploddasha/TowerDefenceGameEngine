package model.fromEditing

import javafx.beans.property.SimpleListProperty
import tornadofx.*

class Mobs(list: List<Mob>? = null, mob: Mob? = null) {
    val listOfMobsProperty = SimpleListProperty(this, "Mobs", list!!.observable())
    var listOfMobs by listOfMobsProperty

}

class MobsModel : ViewModel() {
    val mobsList = observableListOf<Mob>().toObservable()

    fun addMob(mob: Mob) {
        mobsList.add(mob)
    }

    fun addMobs(mobList: List<Mob>) {
        this.mobsList.addAll(mobList)
    }
}