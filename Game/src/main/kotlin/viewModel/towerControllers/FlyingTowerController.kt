package viewModel.towerControllers

import model.tower.FlyingTower
import model.tower.GroundTower

class FlyingTowerController : TowerController {

    override fun getPrice(): Int {
        // тут нужно брать из конфигов
        // для этого надо согласовать формат конфигов
        return 100
    }

    override fun createTower(): FlyingTower {
        val newTower = FlyingTower();
        return newTower;
    }
}