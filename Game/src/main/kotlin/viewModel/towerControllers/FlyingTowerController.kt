package viewModel.towerControllers

import model.tower.FlyingTower
import model.tower.Tower

class FlyingTowerController : TowerController {

    override fun getPrice(tower: Tower): Int {
        return tower.cost
    }

    override fun createTower(
        health : Int,
        fileName : String,
        damage : Int,
        range : Int,
        cost : Int,
        name : String
    ): FlyingTower {
        val newTower = FlyingTower(health, fileName, damage, range, cost, name);
        return newTower;
    }
}