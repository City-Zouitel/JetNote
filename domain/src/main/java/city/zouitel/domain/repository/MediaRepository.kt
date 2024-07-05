package city.zouitel.domain.repository

import city.zouitel.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    val getAllMedias: Flow<List<Media>>

    fun addMedia(media: Media)

    fun updateMedia(media: Media)

    fun deleteMedia(media: Media)
}