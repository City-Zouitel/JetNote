package city.zouitel.domain.repository

import city.zouitel.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    val observeAll: Flow<List<Media>>

    fun observeByUid(uid: String): Flow<List<Media>>

    fun insert(media: Media)

    fun updateMedia(media: Media)

    fun deleteById(id: Long)
}