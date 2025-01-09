package city.zouitel.repository.datasource

import city.zouitel.repository.model.Link
import kotlinx.coroutines.flow.Flow

interface LinkDataSource {

    val observeAll: Flow<List<Link>>

    suspend fun observeByUid(uid: String): Flow<List<Link>>

    suspend fun insert(link: Link)

    suspend fun deleteByUid(uid: String)
}