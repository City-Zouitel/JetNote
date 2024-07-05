package city.zouitel.repository.datasource

import city.zouitel.repository.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaDataSource {

    val getAllMedias: Flow<List<Media>>

    fun addMedia(media: Media)

    fun updateMedia(media: Media)

    fun deleteMedia(media: Media)
}