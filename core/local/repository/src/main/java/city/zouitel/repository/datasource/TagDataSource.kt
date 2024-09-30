package city.zouitel.repository.datasource

import city.zouitel.repository.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagDataSource {
    val getAllLabels:Flow<List<Tag>>

    suspend fun addTag(tag: Tag)

    suspend fun updateTag(tag: Tag)

    suspend fun deleteTag(tag: Tag)
}