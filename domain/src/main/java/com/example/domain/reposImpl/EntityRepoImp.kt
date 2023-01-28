package com.example.domain.reposImpl

import com.example.local.model.Entity
import com.example.domain.repos.EntityRepo
import com.example.local.daos.EntityDao
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class EntityRepoImp @Inject constructor(
    private val dao: EntityDao
): com.example.domain.repos.EntityRepo {
    override val getAllNotesById: Flow<List<Entity>>
        get() = dao.allEntitiesOrderedById()

    override val getAllNotesByName: Flow<List<Entity>>
        get() = dao.allEntitiesOrderedByName()

    override val getAllNotesByNewest: Flow<List<Entity>>
        get() = dao.allEntitiesOrderedByNewest()

    override val getAllNotesByOldest: Flow<List<Entity>>
        get() = dao.allEntitiesOrderedByOldest()

    override val getAllTrashedNotes: Flow<List<Entity>>
        get() = dao.allTrashedNotes()

    override val allNotesByPriority: Flow<List<Entity>>
        get() = dao.allEntitiesOrderedByPriority()

    override val getAllRemindingNotes: Flow<List<Entity>>
        get() = dao.allRemindingNotes()

}