package city.zouitel.repository.model

import androidx.annotation.Keep
import city.zouitel.domain.utils.Constants.DEFAULT_LONG
import city.zouitel.domain.utils.Constants.DEFAULT_TXT

/**
 * Represents an audio file with its metadata.
 *
 * This data class holds information about an audio track, including its unique identifier,
 * title, file path, URI, size, and duration.
 *
 * @property id A unique numerical identifier for this audio. It is mutable and may be
 *             automatically assigned or changed. Defaults to [DEFAULT_LONG].
 * @property uid A unique string identifier for this audio. This is typically a persistent ID
 *             and should remain constant throughout the audio's lifecycle. Defaults to [DEFAULT_TXT].
 * @property title The title of the audio track. Defaults to [DEFAULT_TXT].
 * @property path The file path where the audio is stored on the device's storage.
 *               Defaults to [DEFAULT_TXT].
 * @property uri The content URI of the audio, allowing it to be accessed via the
 *              ContentResolver. Defaults to [DEFAULT_TXT].
 * @property size The size of the audio file in bytes. Defaults to [DEFAULT_LONG].
 * @property duration The duration of the audio track in milliseconds. Defaults to [DEFAULT_LONG].
 */
@Keep
data class Audio(
    var id: Long = DEFAULT_LONG,
    val uid: String = DEFAULT_TXT,
    var title: String = DEFAULT_TXT,
    var path: String = DEFAULT_TXT,
    var uri: String = DEFAULT_TXT,
    var size: Long = DEFAULT_LONG,
    var duration: Long = DEFAULT_LONG
)