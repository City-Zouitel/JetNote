package city.zouitel.domain.model

import androidx.annotation.Keep

@Keep
data class NoteAndTask(
    val noteUid:String,
    val taskId:Long
)