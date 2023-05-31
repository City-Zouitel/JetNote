package com.example.local.datasourceImpl

import com.example.local.dao.LinkDao
import com.example.local.mapper.LinkMapper
import com.example.repository.datasource.LinkDataSource
import com.example.repository.model.Link as OutLink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LinkDataSourceImpl @Inject constructor(
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