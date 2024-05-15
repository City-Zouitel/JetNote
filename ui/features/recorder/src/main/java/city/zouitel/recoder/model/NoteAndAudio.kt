package city.zouitel.recoder.model

import androidx.annotation.Keep

@Keep
data class NoteAndAudio(
    val noteUid: String,
    val audioId: Long
)
