package city.zouitel.note.model

import androidx.annotation.Keep

@Keep
data class Data(
    var uid: String = "",
    var title: String = "",
    var description: String = "",
    var priority: Int = 0,
    var background: Int = 0,
    var textColor: Int = 0x000000,
    var date: String = "",
    var archived: Boolean = false
)
