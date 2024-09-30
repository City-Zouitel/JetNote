package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.LinkDao
import city.zouitel.database.mapper.LinkMapper
import city.zouitel.repository.datasource.LinkDataSource
import city.zouitel.repository.model.Link as OutLink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LinkDataSourceImpl(
    private val dao: LinkDao,
    private val mapper: LinkMapper
): LinkDataSource {
    override val getAllLinks: Flow<List<OutLink>>
        get() = dao.getAllLinks().map { links -> mapper.toRepo(links) }

    override suspend fun addLink(link: OutLink) {
        dao.addLink(mapper.fromRepo(link))
    }

    override suspend fun deleteLink(link: OutLink) {
        dao.deleteLink(mapper.fromRepo(link))
    }
}