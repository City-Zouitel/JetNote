package city.zouitel.links.model

import androidx.annotation.Keep

@Keep
data class NoteAndLink(
    val noteUid: String,
    val linkId: Long
)

