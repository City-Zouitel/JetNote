package city.zouitel.audio.model

import androidx.annotation.Keep

@Keep
data class Audio(
    var id: Long = 0L,
    val uid: String = "",
    var title: String = "Unknown",
    var path: String = "",
    var uri: String = "",
    var size: Long = 0L,
    var duration: Long = 0L,
) {
    val nameWithoutFormat: String get() = title.substringBeforeLast('.')
}