package model

import kotlinx.serialization.Serializable

@Serializable
data class TilePair(val first: Int, val second: Int) {
    override fun hashCode(): Int {
        return first.hashCode() * 31 + second.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TilePair) return false
        return this.first == other.first && this.second == other.second
    }
}