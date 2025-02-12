package city.zouitel.audio.model

import androidx.annotation.Keep
import city.zouitel.domain.utils.Constants.DEFAULT_LONG
import city.zouitel.domain.utils.Constants.DEFAULT_TXT

@Keep
data class Record(
    val id: Long = DEFAULT_LONG,
    val uid: String = DEFAULT_TXT,
    val title: String = DEFAULT_TXT,
    val path: String = DEFAULT_TXT,
    val duration: Long = DEFAULT_LONG
)