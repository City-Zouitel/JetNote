package city.zouitel.domain.repository

import city.zouitel.domain.model.Link
import kotlinx.coroutines.flow.Flow

interface LinkRepository {

    val observeAll: Flow<List<Link>>

    suspend fun observeByUid(uid: String): Flow<List<Link>>

    suspend fun insert(link: Link)

    suspend fun deleteByUid(uid: String)
}