package com.example.repository.repositoryImpl

import com.example.domain.model.Tag as OutTag
import com.example.domain.repository.TagRepository
import com.example.repository.datasource.TagDataSource
import com.example.repository.mapper.TagMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val dataSource: TagDataSource,
    private val mapper: TagMapper
): TagRepository {
    override val getAllTags: Flow<List<OutTag>>
        get() = dataSource.getAllLabels.map { list ->
            list.map { tag ->
                mapper.readOnly(tag)
            }
        }

    override suspend fun addTag(tag: OutTag) {
        dataSource.addTag(mapper.toRepository(tag))
    }

    override suspend fun updateTag(tag: OutTag) {
        dataSource.updateTag(mapper.toRepository(tag))
    }

    override suspend fun deleteTag(tag: OutTag) {
        dataSource.deleteTag(mapper.toRepository(tag))
    }
}