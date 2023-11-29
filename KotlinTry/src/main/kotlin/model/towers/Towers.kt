package model.towers

import javafx.beans.property.SimpleListProperty
import tornadofx.*

class Towers(list: List<Tower>? = null, Tower: Tower? = null) {
    val listOfTowersProperty = SimpleListProperty(this, "Towers", list!!.observable())
    var listOfTowers by listOfTowersProperty

    fun addTower(Tower: Tower) {
        listOfTowers.add(Tower)
    }


}

class TowersModel : ViewModel() {
    val TowerViewModel = TowerModel()

    val TowersList = observableListOf<Tower>().toObservable()

    fun addTower(Tower: Tower) {
        TowersList.add(Tower)
    }
}

//val listOfTowers = bind(Towers::listOfTowers)

