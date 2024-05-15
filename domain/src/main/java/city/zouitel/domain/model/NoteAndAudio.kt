package city.zouitel.domain.model

import androidx.annotation.Keep

@Keep
data class NoteAndAudio(
    val noteUid: String,
    val audioId: Long
)
