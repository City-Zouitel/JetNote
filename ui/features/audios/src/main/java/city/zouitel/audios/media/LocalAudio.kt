package city.zouitel.audios.media

import android.net.Uri

data class LocalAudio(
    val id: String,
    val uri: Uri,
    val path: String,
    val name: String,
    val duration: Long,
    val size: Long
) {
    val nameWithoutFormat: String get() = name.substringBeforeLast('.')
    val format: String get() = name.substringAfterLast('.')
}