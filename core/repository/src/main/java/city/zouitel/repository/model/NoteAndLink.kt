package city.zouitel.repository.model

import androidx.annotation.Keep

@Keep
data class NoteAndLink(
    val noteUid: String,
    val linkId: Long
)
