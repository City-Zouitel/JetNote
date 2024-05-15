package city.zouitel.domain.model

import androidx.annotation.Keep

@Keep
data class Audio(
    var id: Long = 0L,
    var title: String = "Unknown",
    var path: String = "",
    var uri: String = "",
    var size: Long = 0L,
    var duration: Long = 0L,
)