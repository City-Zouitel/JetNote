package com.example.domain.reposImpl

import com.example.domain.repos.LabelRepo
import com.example.local.daos.LabelDao
import com.example.local.model.Label
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class LabelRepoImp @Inject constructor(
    private val dao: LabelDao
): LabelRepo {

    override val getAllLabels: Flow<List<Label>>
        get() = dao.getAllLabels()

    override suspend fun addLabel(label: Label) {
        coroutineScope { launch { dao.addLabel(label) } }
    }

    override suspend fun updateLabel(label: Label) {
        coroutineScope { launch { dao.updateLabel(label) } }
    }

    override suspend fun deleteLabel(label: Label) {
        coroutineScope { launch { dao.deleteLabel(label) } }
    }
}