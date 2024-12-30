package city.zouitel.domain.model

import androidx.annotation.Keep
import city.zouitel.domain.utils.Constants.DEFAULT_BOOLEAN
import city.zouitel.domain.utils.Constants.DEFAULT_NUM
import city.zouitel.domain.utils.Constants.DEFAULT_TXT

/**
 * Represents a media item, such as an image or video, stored locally.
 *
 * @property id A unique identifier for this media item. Defaults to [DEFAULT_NUM].
 * @property uid A unique identifier that may be assigned externally or by a remote system for the media item. Defaults to [DEFAULT_TXT].
 * @property isVideo `true` if this media item is a video, `false` if it is an image. Defaults to [DEFAULT_BOOLEAN].
 * @property uri The file uri to the media item on the device's storage. Defaults to [DEFAULT_TXT].
 *
 * @see DEFAULT_NUM
 * @see DEFAULT_TXT
 * @see DEFAULT_BOOLEAN
 */
@Keep
data class Media(
    val id: Long = DEFAULT_NUM,
    val uid: String = DEFAULT_TXT,
    val isVideo: Boolean = DEFAULT_BOOLEAN,
    val uri: String = DEFAULT_TXT
)