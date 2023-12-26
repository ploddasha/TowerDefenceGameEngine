package model.fromEditing

import javafx.beans.property.SimpleListProperty
import tornadofx.*

class Towers(list: List<Tower>? = null) {
    val listOfTowersProperty = SimpleListProperty(this, "Towers", list!!.observable())
    var listOfTowers by listOfTowersProperty

    fun addTower(Tower: Tower) {
        listOfTowers.add(Tower)
    }


}

class TowersModel : ViewModel() {
    var selectedTower = Tower("NoName", "ggg", 500, 500, 500, TowerType.Walk, 500)

    val TowerViewModel = TowerModel()

    val towersList = observableListOf<Tower>().toObservable()

    fun addTower(Tower: Tower) {
        towersList.add(Tower)
    }


    fun selectTower(tower: Tower) {
        selectedTower = tower
    }

}