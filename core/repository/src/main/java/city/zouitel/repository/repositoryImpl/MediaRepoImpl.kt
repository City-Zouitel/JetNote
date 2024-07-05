package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Media
import city.zouitel.domain.repository.MediaRepository
import city.zouitel.repository.datasource.MediaDataSource
import city.zouitel.repository.mapper.MediaMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MediaRepoImpl(
    private val dataSource: MediaDataSource,
    private val mapper: MediaMapper
): MediaRepository {
    override val getAllMedias: Flow<List<Media>>
        get() = dataSource.getAllMedias.map { audios -> mapper.toDomain(audios) }

    override fun addMedia(media: Media) {
        dataSource.addMedia(mapper.fromDomain(media))
    }

    override fun updateMedia(media: Media) {
        dataSource.updateMedia(mapper.fromDomain(media))
    }

    override fun deleteMedia(media: Media) {
        dataSource.deleteMedia(mapper.fromDomain(media))
    }
}