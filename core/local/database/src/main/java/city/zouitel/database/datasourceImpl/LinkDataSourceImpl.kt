package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.LinkDao
import city.zouitel.database.mapper.LinkMapper
import city.zouitel.repository.datasource.LinkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.repository.model.Link as OutLink

class LinkDataSourceImpl(
    private val dao: LinkDao,
    private val mapper: LinkMapper
): LinkDataSource {
    override val observeAll: Flow<List<OutLink>>
        get() = dao.observeAll.map { mapper.toRepo(it) }

    override suspend fun observeByUid(uid: String): Flow<List<OutLink>> {
        return dao.observeByUid(uid).map { mapper.toRepo(it) }
    }

    override suspend fun insert(link: OutLink) {
        dao.insert(mapper.fromRepo(link))
    }

    override suspend fun deleteByUid(uid: String) {
        dao.deleteByUid(uid)
    }
}