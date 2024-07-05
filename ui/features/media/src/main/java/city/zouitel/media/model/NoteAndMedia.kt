package city.zouitel.media.model

import androidx.annotation.Keep

@Keep
data class NoteAndMedia(
    val noteUid: String,
    val mediaId: Long
)