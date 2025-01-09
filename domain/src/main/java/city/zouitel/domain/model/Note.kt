package city.zouitel.domain.model

import androidx.annotation.Keep

@Keep
data class Note(
    val dataEntity: Data,
    val tagEntities: List<Tag>,
    val audioEntities: List<Audio>
)
