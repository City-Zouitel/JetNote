package city.zouitel.tasks.model

import androidx.annotation.Keep

@Keep
data class Task(
    val id:Long = 0L,
    var item:String? = null,
    var isDone:Boolean = false
)
