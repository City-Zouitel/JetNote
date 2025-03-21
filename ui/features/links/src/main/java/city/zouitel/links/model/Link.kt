package city.zouitel.links.model

import androidx.annotation.Keep
import city.zouitel.domain.utils.ModelConstants.DEFAULT_INT
import city.zouitel.domain.utils.ModelConstants.DEFAULT_TXT

@Keep
data class Link(
     val id: Int = DEFAULT_INT,
     val uid: String = DEFAULT_TXT,
     val url: String = DEFAULT_TXT,
     val title: String = DEFAULT_TXT,
     val description: String = DEFAULT_TXT,
     val image: String = DEFAULT_TXT,
     val icon: String = DEFAULT_TXT
)