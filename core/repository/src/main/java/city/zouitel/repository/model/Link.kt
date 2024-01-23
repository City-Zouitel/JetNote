package city.zouitel.repository.model

import androidx.annotation.Keep

@Keep
data class Link(
     var id: String = "",
     var url: String = "",
     var host: String = "",
     var image: String? = "",
     val title: String? = "",
     var description: String? = ""
)