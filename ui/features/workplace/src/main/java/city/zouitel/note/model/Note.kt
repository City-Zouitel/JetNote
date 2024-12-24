package city.zouitel.note.model

import androidx.annotation.Keep
import city.zouitel.audios.model.Audio
import city.zouitel.links.model.Link
import city.zouitel.media.model.Media
import city.zouitel.tags.model.Tag

@Keep
data class Note(
    val dataEntity: Data,
    val tagEntities: List<Tag>,
    val linkEntities: List<Link>,
    val audioEntities: List<Audio>,
    val mediaEntities: List<Media>
)
