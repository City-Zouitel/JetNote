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
    override val observeAll: Flow<List<Media>>
        get() = dao.observeAll.map { mapper.toRepo(it) }

    override fun observeByUid(uid: String): Flow<List<Media>> {
        return dao.observeByUid(uid).map { mapper.toRepo(it) }
    }

    override fun insert(media: Media) {
        dao.insert(mapper.fromRepo(media))
    }

    override fun updateMedia(media: Media) {
        dao.updateMedia(mapper.fromRepo(media))
    }

    override fun deleteById(id: Long) {
        dao.deleteById(id)
    }
}