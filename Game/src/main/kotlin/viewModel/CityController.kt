package viewModel

import javafx.beans.property.SimpleIntegerProperty
import model.CityModel

class CityController {
    private var city = CityModel()

    private val cityProperty = SimpleIntegerProperty(city.getHealth())
    fun cityProperty() = cityProperty

    fun getCityHealth(): Int {
        //return this.city.getHealth()
        return cityProperty.get()
    }

    fun addCityHealth(addingHealth: Int) {
        /*
        val currHealth = this.city.getHealth()
        this.city.setHealth(currHealth + addingHealth)
        return
         */
        cityProperty.set(cityProperty.get() + addingHealth)

    }

    fun subtractCityHealth(subtractingHealth: Int) {
        /*
        val currHealth = this.city.getHealth()
        this.city.setHealth(currHealth - subtractingHealth)
        return */
        cityProperty.set(cityProperty.get() - subtractingHealth)

    }
}