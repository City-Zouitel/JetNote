package city.zouitel.repository.model

import androidx.annotation.Keep
import city.zouitel.repository.utils.Constants.NON

@Keep
data class Data(
    var uid: String = "",
    var title: String? = null,
    var description: String? = null,
    var priority: String = NON,
    var color: Int = 0,
    var textColor: Int = 0x000000,
    var date: String = "",
    var removed: Int = 0,
    var reminding: Long = 0L,
)
