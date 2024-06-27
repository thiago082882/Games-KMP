package data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Games(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("imgUrl")
    val imgUrl: String? = null,
    @SerialName("shortDescription")
    val shortDescription: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("year")
    val year: Int? = null,
)