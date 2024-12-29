package city.zouitel.repository.datasource

import city.zouitel.repository.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaDataSource {
    val observeAll: Flow<List<Media>>

    fun observeByUid(uid: String): Flow<List<Media>>

    fun insert(media: Media)

    fun updateMedia(media: Media)

    fun deleteById(id: Long)
}