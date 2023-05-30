package com.example.domain.reposImpl

import com.example.domain.repos.LabelRepo
import com.example.local.dao.TagDao
import com.example.local.model.TagEntity
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class LabelRepoImpl @Inject constructor(
    private val dao: TagDao
): LabelRepo {

    override val getAllLabels: Flow<List<TagEntity>>
        get() = dao.getAllLabels()

    override suspend fun addLabel(tagEntity: TagEntity) {
        coroutineScope { launch { dao.addLabel(tagEntity) } }
    }

    override suspend fun updateLabel(tagEntity: TagEntity) {
        coroutineScope { launch { dao.updateLabel(tagEntity) } }
    }

    override suspend fun deleteLabel(tagEntity: TagEntity) {
        coroutineScope { launch { dao.deleteLabel(tagEntity) } }
    }
}