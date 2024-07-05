package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.MediaDao
import city.zouitel.database.mapper.MediaMapper
import city.zouitel.repository.datasource.MediaDataSource
import city.zouitel.repository.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MediaDataSourceImpl(
    private val dao: MediaDao,
    private val mapper: MediaMapper
): MediaDataSource {
    override val getAllMedias: Flow<List<Media>>
        get() = dao.getAllMedias().map { medias -> mapper.toRepo(medias) }

    override fun addMedia(media: Media) {
        dao.addMedia(mapper.fromRepo(media))
    }

    override fun updateMedia(media: Media) {
        dao.updateMedia(mapper.fromRepo(media))
    }

    override fun deleteMedia(media: Media) {
        dao.deleteMedia(mapper.fromRepo(media))
    }
}