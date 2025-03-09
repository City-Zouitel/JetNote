package city.zouitel.domain.model

import androidx.annotation.Keep
import city.zouitel.domain.utils.ModelConstants.DEFAULT_BOOLEAN
import city.zouitel.domain.utils.ModelConstants.DEFAULT_INT
import city.zouitel.domain.utils.ModelConstants.DEFAULT_TXT

@Keep
data class Data(
    var uid: String = DEFAULT_TXT,
    var title: String = DEFAULT_TXT,
    var description: String = DEFAULT_TXT,
    var priority: Int = DEFAULT_INT,
    var background: Int = DEFAULT_INT,
    var textColor: Int = DEFAULT_INT,
    var date: String = DEFAULT_TXT,
    var archived: Boolean = DEFAULT_BOOLEAN
)
