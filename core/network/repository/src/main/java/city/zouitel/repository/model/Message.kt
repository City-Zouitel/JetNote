package city.zouitel.repository.model

import androidx.annotation.Keep

@Keep
data class Message(
    val id: Long = 0L,
    val isRequest: Boolean = false,
    /*val image: Bitmap? = null,*/
    val prompt: String = "",
)
