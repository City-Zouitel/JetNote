package city.zouitel.domain.model

import androidx.annotation.Keep

@Keep
data class Media(
    val id: Long = 0,
    val isVideo: Boolean = false,
    val path: String = ""
)