package city.zouitel.repository.model

import androidx.annotation.Keep

@Keep
data class NoteAndTask(
    val noteUid:String,
    val taskId:Long
)