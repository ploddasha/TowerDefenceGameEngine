package model

import javafx.beans.property.SimpleListProperty
import tornadofx.*

class Mobs(list: List<Mob>? = null, mob: Mob? = null) {
    val listOfMobsProperty = SimpleListProperty(this, "Mobs", list!!.observable())
    var listOfMobs by listOfMobsProperty

    fun addMob(mob: Mob) {
        listOfMobs.add(mob)
    }


}

class MobsModel : ViewModel() {
    val mobViewModel = MobModel()

    val mobsList = observableListOf<Mob>().toObservable()

    fun addMob(mob: Mob) {
        mobsList.add(mob)
    }
}

//val listOfMobs = bind(Mobs::listOfMobs)

