package viewModel.towerControllers

import model.tower.GroundTower
import model.tower.Tower

class GroundTowerController() : TowerController {

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
    ): GroundTower {
        val newTower = GroundTower(health, fileName, damage, range, cost, name);
        return newTower;
    }
}