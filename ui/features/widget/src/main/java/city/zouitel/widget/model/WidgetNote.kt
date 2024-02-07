package city.zouitel.widget.model

import androidx.annotation.Keep
import city.zouitel.links.model.Link
import city.zouitel.note.model.Data
import city.zouitel.tags.model.Tag
import city.zouitel.tasks.model.Task

@Keep
data class WidgetNote(
    val dataEntity: Data,
    val tagEntities: List<Tag>,
    val taskEntities: List<Task>,
    val linkEntities: List<Link>
)