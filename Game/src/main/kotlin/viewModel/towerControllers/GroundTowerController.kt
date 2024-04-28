package viewModel.towerControllers

import model.tower.GroundTower

class GroundTowerController : TowerController {

    override fun getPrice() {
        // тут нужно брать из конфигов
        // для этого надо согласовать формат конфигов
    }

    override fun createTower(): GroundTower {
        val newTower = GroundTower();
        return newTower;
    }
}