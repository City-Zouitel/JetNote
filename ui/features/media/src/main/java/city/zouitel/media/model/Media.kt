package city.zouitel.media.model

import androidx.annotation.Keep

@Keep
data class Media(
    val id: Long = 0,
    val isVideo: Boolean = false,
    val path: String = ""
)