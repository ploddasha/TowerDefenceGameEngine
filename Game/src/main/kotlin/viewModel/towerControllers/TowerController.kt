package viewModel.towerControllers

import model.fromEditing.TowerType
import model.tower.FlyingTower
import model.tower.GroundTower
import model.tower.Tower

interface TowerController {
    fun getPrice(tower: Tower): Int
    fun createTower(
        health : Int,
        fileName : String,
        damage : Int,
        range : Int,
        cost : Int,
        name : String,
        type : TowerType
    ): Tower
}