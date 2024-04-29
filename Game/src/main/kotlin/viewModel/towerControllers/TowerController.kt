package viewModel.towerControllers

import model.tower.FlyingTower
import model.tower.GroundTower
import model.tower.Tower

interface TowerController {
    fun getPrice(): Int
    fun createTower(): Tower
}