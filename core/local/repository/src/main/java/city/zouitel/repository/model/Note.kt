package city.zouitel.repository.model

import androidx.annotation.Keep

@Keep
data class Note(
    val dataEntity: Data,
    val tagEntities: List<Tag>,
    val linkEntities: List<Link>,
    val audioEntities: List<Audio>
)
