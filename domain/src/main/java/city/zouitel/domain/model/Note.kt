package city.zouitel.domain.model

import androidx.annotation.Keep

@Keep
data class Note(
    val dataEntity: Data,
    val tagEntities: List<Tag>,
    val linkEntities: List<Link>,
    val audioEntities: List<Audio>,
    val mediaEntities: List<Media>
)
