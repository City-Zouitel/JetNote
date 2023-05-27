package com.example.local.repository.data_source_impl

import com.example.local.dao.LinkDao
import com.example.local.model.Link
import com.example.local.repository.data_source.LinkDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LinkDataSourceImpl @Inject constructor(
    linkDao: LinkDao
): LinkDataSource {
    override val getAllLinks: Flow<List<Link>>
        get() = TODO("Not yet implemented")

    override suspend fun addLink(link: Link) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLink(link: Link) {
        TODO("Not yet implemented")
    }
}