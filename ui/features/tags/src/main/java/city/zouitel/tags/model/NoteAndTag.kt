package city.zouitel.tags.model

import androidx.annotation.Keep

@Keep
data class NoteAndTag(
    val noteUid: String,
    val labelId: Long
)
