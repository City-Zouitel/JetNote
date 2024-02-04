package city.zouitel.widget.data

import androidx.annotation.Keep
import city.zouitel.domain.model.Data
import city.zouitel.domain.model.Link
import city.zouitel.domain.model.Tag
import city.zouitel.domain.model.Task

@Keep
data class Note(
    val dataEntity: Data,
    val tagEntities: List<Tag>,
    val taskEntities: List<Task>,
    val linkEntities: List<Link>
)