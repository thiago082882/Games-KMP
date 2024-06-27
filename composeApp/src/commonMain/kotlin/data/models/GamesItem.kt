package data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GamesItem(
    @SerialName("results")
    val results: List<Games>
)