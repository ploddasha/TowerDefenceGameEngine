package viewModel

import model.CityModel

class CityController {
    private var city = CityModel()

    fun getCityHealth(): Int {
        return this.city.getHealth()
    }

    fun addCityHealth(addingHealth: Int) {
        val currHealth = this.city.getHealth()
        this.city.setHealth(currHealth + addingHealth)
        return
    }

    fun subtractCityHealth(subtractingHealth: Int) {
        val currHealth = this.city.getHealth()
        this.city.setHealth(currHealth - subtractingHealth)
        return
    }
}