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
    override val observeAll: Flow<List<Media>>
        get() = dataSource.observeAll.map { mapper.toDomain(it) }

    override fun observeByUid(uid: String): Flow<List<Media>> {
        return dataSource.observeByUid(uid).map { mapper.toDomain(it) }
}

    override fun insert(media: Media) {
        dataSource.insert(mapper.fromDomain(media))
    }

    override fun updateMedia(media: Media) {
        dataSource.updateMedia(mapper.fromDomain(media))
    }

    override fun deleteById(id: Long) {
        dataSource.deleteById(id)
    }
}