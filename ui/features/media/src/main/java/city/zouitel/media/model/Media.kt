package city.zouitel.media.model

import androidx.annotation.Keep
import city.zouitel.domain.utils.ModelConstants.DEFAULT_BOOLEAN
import city.zouitel.domain.utils.ModelConstants.DEFAULT_LONG
import city.zouitel.domain.utils.ModelConstants.DEFAULT_TXT

@Keep
data class Media(
    val id: Long = DEFAULT_LONG,
    val uid: String = DEFAULT_TXT,
    val isVideo: Boolean = DEFAULT_BOOLEAN,
    val uri: String = DEFAULT_TXT
)