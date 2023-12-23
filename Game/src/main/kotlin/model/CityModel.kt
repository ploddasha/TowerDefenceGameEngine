package model

class CityModel {
    private var health: Int = 0
    private var costOfHealthPoint: Int = 0

    fun setHealth(health: Int) {
        this.health = health
        return
    }

    fun setCostOfHealthPoint(cost: Int) {
        this.costOfHealthPoint = cost
        return
    }

    fun getHealth(): Int {
        return this.health
    }

    fun getCostOfHealthPoint(): Int {
        return this.costOfHealthPoint
    }
}