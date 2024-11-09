package city.zouitel.reminder.model

import androidx.annotation.Keep

@Keep
data class NoteAndReminder(
    val noteId: String,
    val reminderId: Long
)