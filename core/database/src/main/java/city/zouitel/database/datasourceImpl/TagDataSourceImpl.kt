package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.TagDao
import city.zouitel.database.mapper.TagMapper
import city.zouitel.repository.datasource.TagDataSource
import city.zouitel.repository.model.Tag as OutTag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TagDataSourceImpl(
    private val dao: TagDao,
    private val mapper: TagMapper
): TagDataSource {
    override val getAllLabels: Flow<List<OutTag>>
        get() = dao.getAllTags().map { tags -> mapper.toRepo(tags) }

    override suspend fun addTag(tag: OutTag) {
        dao.addTag(mapper.fromRepo(tag))
    }

    override suspend fun updateTag(tag: OutTag) {
        dao.updateTag(mapper.fromRepo(tag))
    }

    override suspend fun deleteTag(tag: OutTag) {
        dao.deleteTag(mapper.fromRepo(tag))
    }
}