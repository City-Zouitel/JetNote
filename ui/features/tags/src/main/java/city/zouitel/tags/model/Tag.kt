package city.zouitel.tags.model

import androidx.annotation.Keep

@Keep
data class Tag(
     val id: Long = 0L,
     val label: String = "",
     val color: Int = 0x0000
)