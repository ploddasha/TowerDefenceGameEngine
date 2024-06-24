package viewModel

import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleIntegerProperty

class WaveController {
    private val currentWaveProperty = SimpleIntegerProperty(0)
    fun currentWaveProperty() = currentWaveProperty

    val totalWavesProperty: IntegerProperty = SimpleIntegerProperty(0)

    fun setTotalWaves(totalWaves: Int) {
        totalWavesProperty.set(totalWaves)
    }

    fun getCurrentWave(): Int {
        return currentWaveProperty.get()

    }

    fun setNextWave() {
        currentWaveProperty.set(currentWaveProperty.get() + 1)
    }}