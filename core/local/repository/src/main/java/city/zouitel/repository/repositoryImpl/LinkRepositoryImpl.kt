package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.repository.LinkRepository
import city.zouitel.repository.datasource.LinkDataSource
import city.zouitel.repository.mapper.LinkMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.domain.model.Link as OutLink

class LinkRepositoryImpl(
    private val dataSource: LinkDataSource,
    private val mapper: LinkMapper
): LinkRepository {
    override val observeAll: Flow<List<OutLink>>
        get() = dataSource.observeAll.map { mapper.toDomain(it) }

    override suspend fun observeByUid(uid: String): Flow<List<OutLink>> {
        return dataSource.observeByUid(uid).map { mapper.toDomain(it) }
    }

    override suspend fun insert(link: OutLink) {
        dataSource.insert(mapper.fromDomain(link))
    }

    override suspend fun deleteByUid(uid: String) {
        dataSource.deleteByUid(uid)
    }
}