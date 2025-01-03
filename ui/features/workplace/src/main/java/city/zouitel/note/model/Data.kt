package city.zouitel.note.model

import androidx.annotation.Keep

@Keep
data class Data(
    var uid: String = "",
    var title: String? = null,
    var description: String? = null,
    var priority: String = "NON",
    var color: Int = 0,
    var textColor: Int = 0x000000,
    var date: String = "",
    var removed: Int = 0
)
