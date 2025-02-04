package city.zouitel.note.model

import androidx.annotation.Keep
import city.zouitel.tags.model.Tag

@Keep
data class Note(
    val data: Data,
    val tags: List<Tag>
)
