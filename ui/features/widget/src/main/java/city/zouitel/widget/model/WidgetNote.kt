package city.zouitel.widget.model

import androidx.annotation.Keep
import city.zouitel.note.model.Data
import city.zouitel.tags.model.Tag

@Keep
data class WidgetNote(
    val data: Data,
    val tags: List<Tag>,
)