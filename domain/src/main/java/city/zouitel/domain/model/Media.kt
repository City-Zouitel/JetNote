package city.zouitel.domain.model

import androidx.annotation.Keep

@Keep
data class Media(
    val id: Long = 0,
    val uid: String = "",
    val isVideo: Boolean = false,
    val uri: String = ""
)