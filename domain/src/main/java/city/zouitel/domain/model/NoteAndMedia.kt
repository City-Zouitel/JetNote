package city.zouitel.domain.model

import androidx.annotation.Keep

@Keep
data class NoteAndMedia(
    val noteUid: String,
    val mediaId: Long
)