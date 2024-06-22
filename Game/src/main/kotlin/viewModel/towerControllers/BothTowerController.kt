package viewModel.towerControllers

import model.fromEditing.TowerType
import model.tower.BothTower
import model.tower.Tower

class BothTowerController : TowerController {

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
    ): BothTower {
        val newTower = BothTower(health, fileName, damage, range, cost, name, type);
        return newTower;
    }
}