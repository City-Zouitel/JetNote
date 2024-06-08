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
        get() = dao.getAllTags().map { list ->
            list.map {
                mapper.readOnly(it)
            }
        }

    override suspend fun addTag(tag: OutTag) {
        dao.addTag(mapper.toLocal(tag))
    }

    override suspend fun updateTag(tag: OutTag) {
        dao.updateTag(mapper.toLocal(tag))
    }

    override suspend fun deleteTag(tag: OutTag) {
        dao.deleteTag(mapper.toLocal(tag))
    }
}