package com.example.local.datasourceImpl

import com.example.local.dao.TagDao
import com.example.local.mapper.TagMapper
import com.example.repository.datasource.TagDataSource
import com.example.repository.model.Tag as OutTag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TagDataSourceImpl @Inject constructor(
    private val tagDao: TagDao,
    private val tagMapper: TagMapper
): TagDataSource {
    override val getAllLabels: Flow<List<OutTag>>
        get() = tagDao.getAllTags().map { list ->
            list.map {
                tagMapper.readOnly(it)
            }
        }

    override suspend fun addTag(tag: OutTag) {
        tagDao.addTag(tagMapper.toLocal(tag))
    }

    override suspend fun updateTag(tag: OutTag) {
        tagDao.updateTag(tagMapper.toLocal(tag))
    }

    override suspend fun deleteTag(tag: OutTag) {
        tagDao.deleteTag(tagMapper.toLocal(tag))
    }
}