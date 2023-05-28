package com.example.local.datasourceImpl

import com.example.local.dao.LinkDao
import com.example.local.mapper.LinkMapper
import com.example.repository.datasource.LinkDataSource
import com.example.repository.model.Link
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LinkDataSourceImpl @Inject constructor(
    private val linkDao: LinkDao,
    private val linkMapper: LinkMapper
): LinkDataSource {
    override val getAllLinks: Flow<List<Link>>
        get() = linkDao.getAllLinks().map {  list ->
            list.map { link ->
                linkMapper.toRepository(link)
            }
        }

    override suspend fun addLink(link: Link) {
        linkDao.addLink(linkMapper.toLocal(link))
    }

    override suspend fun deleteLink(link: Link) {
        linkDao.deleteLink(linkMapper.toLocal(link))
    }
}