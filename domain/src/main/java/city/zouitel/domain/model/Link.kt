package city.zouitel.domain.model

import androidx.annotation.Keep

@Keep
data class Link(
     var id: Long = 0L,
     var url: String = "",
     var host: String = "",
     var image: String? = "",
     val title: String? = "",
     var description: String? = ""
)