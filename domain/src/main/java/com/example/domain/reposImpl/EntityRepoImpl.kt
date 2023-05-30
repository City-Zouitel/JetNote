package com.example.domain.reposImpl

import com.example.local.model.relational.MainEntity
import com.example.domain.repos.EntityRepo
import com.example.local.dao.EntityDao
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class EntityRepoImpl @Inject constructor(
    private val dao: EntityDao
): EntityRepo {
    override val getAllNotesById: Flow<List<MainEntity>>
        get() = dao.getAllEntitiesById()

    override val getAllNotesByName: Flow<List<MainEntity>>
        get() = dao.getAllEntitiesByName()

    override val getAllNotesByNewest: Flow<List<MainEntity>>
        get() = dao.getAllEntitiesByNewest()

    override val getAllNotesByOldest: Flow<List<MainEntity>>
        get() = dao.getAllEntitiesByOldest()

    override val getAllTrashedNotes: Flow<List<MainEntity>>
        get() = dao.getAllTrashedNotes()

    override val allNotesByPriority: Flow<List<MainEntity>>
        get() = dao.getAllEntitiesByPriority()

    override val getAllRemindingNotes: Flow<List<MainEntity>>
        get() = dao.getAllRemindingNotes()

}