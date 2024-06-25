package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Link as OutLink
import city.zouitel.domain.repository.LinkRepository
import city.zouitel.repository.datasource.LinkDataSource
import city.zouitel.repository.mapper.LinkMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LinkRepositoryImpl(
    private val dataSource: LinkDataSource,
    private val mapper: LinkMapper
): LinkRepository {
    override val getAllLinks: Flow<List<OutLink>>
        get() = dataSource.getAllLinks.map { links -> mapper.toDomain(links) }

    override suspend fun addLink(link: OutLink) {
        dataSource.addLink(mapper.fromDomain(link))
    }

    override suspend fun deleteLink(link: OutLink) {
        dataSource.deleteLink(mapper.fromDomain(link))
    }
}