package model.data

import kotlinx.serialization.Serializable


@Serializable
data class Game(val id: Int, val gameName: String)