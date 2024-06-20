package model.data

import kotlinx.serialization.Serializable
import viewModel.real.RealMob
import viewModel.real.RealTower

@Serializable
data class GameState(
    val isGameOn: Boolean,
    val moneyAmount: Int,
    val cityHealth: Int,
    val rating: Int,
    val currentWave: Int,
    val mobs: List<RealMob>,
    val towers: List<RealTower>
)