package viewModel.towerControllers

import model.tower.GroundTower
import model.tower.Tower
import model.fromEditing.TowerType

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
        name : String,
        type : TowerType
    ): GroundTower {
        val newTower = GroundTower(health, fileName, damage, range, cost, name, type);
        return newTower;
    }
}