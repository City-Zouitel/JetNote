package city.zouitel.api

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Invalid(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("word")
    val word: String = ""
)
