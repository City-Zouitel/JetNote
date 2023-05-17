package com.example.domain.reposImpl

import com.example.domain.repos.LinkRepo
import com.example.local.daos.LinkDao
import com.example.local.model.Link
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

class LinkRepoImpl @Inject constructor(
    private val dao: LinkDao
): LinkRepo {
    override val getAllLinks: Flow<List<Link>>
        get() = dao.getAllLinks()

    override suspend fun addLink(link: Link) {
        coroutineScope { launch { dao.addLink(link) } }
    }

    override suspend fun deleteLink(link: Link) {
        coroutineScope { launch { dao.deleteLink(link) } }
    }
}