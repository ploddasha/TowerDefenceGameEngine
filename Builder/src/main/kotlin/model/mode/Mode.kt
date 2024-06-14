package model.mode

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList
import tornadofx.ItemViewModel
import model.Mob
import tornadofx.*

class MobWithCount(
    mob: Mob,
    count: Int? = null
    ){

    val mobProperty = SimpleObjectProperty(this, "mob", mob)
    var mob by mobProperty

    val countProperty = SimpleIntegerProperty(this, "count", count!!)
    var count by countProperty
}

class Mode(
    wavesMode: Boolean? = null,
    countOfWaves: Int? = null,
    listOfWaves: List<List<MobWithCount>>? = null,
    selectedWave: Int? = null
){
    val wavesModeProperty = SimpleBooleanProperty(this, "Waves mode", false)
    var wavesMode by wavesModeProperty

    val countOfWavesProperty = SimpleIntegerProperty(this, "Count of waves", countOfWaves!!)
    var countOfWaves by countOfWavesProperty

    val listOfWavesProperty = SimpleListProperty(listOfWaves!!.observable())
    var listOfWaves by listOfWavesProperty

    val selectedWaveProperty = SimpleIntegerProperty(this, "Selected wave", countOfWaves!!)
    var selectedWave by selectedWaveProperty

}

class ModeModel: ItemViewModel<Mode>() {
    val wavesMode = bind(Mode::wavesMode)
    val countOfWaves = bind(Mode::countOfWaves)
    val selectedWave = bind(Mode::selectedWave)

    //FXCollections.observableArrayList()
    val wavesLists = SimpleListProperty<List<MobWithCount>>().toObservable()

    fun saveInformation(mobs: ObservableList<Mob>) {
        val wave = mobs.map { MobWithCount(it, 10) }
        val result = List(countOfWaves.value) { wave }
        wavesLists.setAll(result)
    }


}
