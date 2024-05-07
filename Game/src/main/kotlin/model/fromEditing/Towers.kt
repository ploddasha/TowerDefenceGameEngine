package model.fromEditing

// It's not from Editing!!!
import javafx.beans.property.SimpleListProperty
import tornadofx.*

class Towers(list: List<Tower>? = null, tower: Tower? = null) {
    val listOfTowersProperty = SimpleListProperty(this, "towers", list!!.observable())
    var listOfTowers by listOfTowersProperty

}

class TowersModel : ViewModel() {
    val towersList = observableListOf<Tower>().toObservable()

    fun addTower(tower: Tower) {
        towersList.add(tower)
    }

    fun addTowers(towerList: List<Tower>) {
        this.towersList.addAll(towerList)
    }
}