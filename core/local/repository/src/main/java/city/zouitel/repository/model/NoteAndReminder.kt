package city.zouitel.repository.model

import androidx.annotation.Keep

@Keep
data class NoteAndReminder(
    val noteId: String,
    val reminderId: Long
)