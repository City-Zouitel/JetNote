package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Link as OutLink
import city.zouitel.domain.repository.LinkRepository
import city.zouitel.repository.datasource.LinkDataSource
import city.zouitel.repository.mapper.LinkMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LinkRepositoryImpl /*@Inject*/ constructor(
    private val dataSource: LinkDataSource,
    private val mapper: LinkMapper
): LinkRepository {
    override val getAllLinks: Flow<List<OutLink>>
        get() = dataSource.getAllLinks.map { list ->
            list.map { link ->
                mapper.toDomain(link)
            }
        }

    override suspend fun addLink(link: OutLink) {
        dataSource.addLink(mapper.toRepository(link))
    }

    override suspend fun deleteLink(link: OutLink) {
        dataSource.deleteLink(mapper.toRepository(link))
    }
}