package model.data

import kotlinx.serialization.Serializable

@Serializable
data class InitConfig(
    val gameName: String,
    val cityHealth: Int,
    val initialMoney: Int
)