package city.zouitel.repository.model

import androidx.annotation.Keep

@Keep
data class Media(
    val id: Long = 0,
    val uid: String = "",
    val isVideo: Boolean = false,
    val path: String = ""
)