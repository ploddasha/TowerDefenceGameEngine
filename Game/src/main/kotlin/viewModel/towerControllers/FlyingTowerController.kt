package viewModel.towerControllers

import model.tower.FlyingTower
import model.tower.GroundTower

class FlyingTowerController : TowerController {

    override fun getPrice() {
        // тут нужно брать из конфигов
        // для этого надо согласовать формат конфигов
    }

    override fun createTower(): FlyingTower {
        val newTower = FlyingTower();
        return newTower;
    }
}