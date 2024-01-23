package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.LinkDao
import city.zouitel.database.mapper.LinkMapper
import city.zouitel.repository.datasource.LinkDataSource
import city.zouitel.repository.model.Link as OutLink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LinkDataSourceImpl /*@Inject*/ constructor(
    private val linkDao: LinkDao,
    private val linkMapper: LinkMapper
): LinkDataSource {
    override val getAllLinks: Flow<List<OutLink>>
        get() = linkDao.getAllLinks().map {  list ->
            list.map {
                linkMapper.readOnly(it)
            }
        }

    override suspend fun addLink(link: OutLink) {
        linkDao.addLink(linkMapper.toLocal(link))
    }

    override suspend fun deleteLink(link: OutLink) {
        linkDao.deleteLink(linkMapper.toLocal(link))
    }
}