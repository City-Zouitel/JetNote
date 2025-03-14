package city.zouitel.domain.model

import androidx.annotation.Keep

@Keep
data class Message(
    val id: Long = 0L,
    val isRequest: Boolean = false,
    val prompt: String = "",
)
