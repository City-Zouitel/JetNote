package city.zouitel.repository.datasource

import city.zouitel.repository.model.Link
import kotlinx.coroutines.flow.Flow

interface LinkDataSource {

    val getAllLinks: Flow<List<Link>>

    suspend fun addLink(link: Link)

    suspend fun deleteLink(link: Link)
}