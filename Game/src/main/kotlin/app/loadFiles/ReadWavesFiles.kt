package app.loadFiles

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File


@Serializable
data class WaveConfig(
    val waves: List<List<MobConfig>>
)

@Serializable
data class MobConfig(
    val name: String,
    val amount: Int
)

