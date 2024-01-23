package city.zouitel.domain.repository

import city.zouitel.domain.model.Link
import kotlinx.coroutines.flow.Flow

interface LinkRepository {

    val getAllLinks: Flow<List<Link>>

    suspend fun addLink(link: Link)

    suspend fun deleteLink(link: Link)
}