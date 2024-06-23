package model.data

import app.loadFiles.Mob
import app.loadFiles.WaveConfig
import kotlinx.serialization.Serializable
import model.fromEditing.Tile
import model.fromEditing.TowerType

@Serializable
data class UnifiedGameData(
    val initData: InitConfig,
    val mapData: List<Tile>,
    val mobsData: List<Mob>,
    val towersData: List<TowerU>,
    val wavesData: WaveConfig
)

@Serializable
data class TowerU(
    val type: TowerType,
    val health: Int,
    val fileName: String,
    val damage: Int,
    val range: Int,
    val cost: Int,
    val name: String
)