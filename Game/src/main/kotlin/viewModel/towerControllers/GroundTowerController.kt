package viewModel.towerControllers

import model.tower.GroundTower

class GroundTowerController : TowerController {

    override fun getPrice(): Int {
        // тут нужно брать из конфигов
        // для этого надо согласовать формат конфигов
        return 100
    }

    override fun createTower(): GroundTower {
        val newTower = GroundTower();
        return newTower;
    }
}