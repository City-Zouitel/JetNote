package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Tag as OutTag
import city.zouitel.domain.repository.TagRepository
import city.zouitel.repository.datasource.TagDataSource
import city.zouitel.repository.mapper.TagMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TagRepositoryImpl(
    private val dataSource: TagDataSource,
    private val mapper: TagMapper
): TagRepository {
    override val getAllTags: Flow<List<OutTag>>
        get() = dataSource.getAllLabels.map { tags -> mapper.toDomain(tags) }

    override suspend fun addTag(tag: OutTag) {
        dataSource.addTag(mapper.fromDomain(tag))
    }

    override suspend fun updateTag(tag: OutTag) {
        dataSource.updateTag(mapper.fromDomain(tag))
    }

    override suspend fun deleteTag(tag: OutTag) {
        dataSource.deleteTag(mapper.fromDomain(tag))
    }
}