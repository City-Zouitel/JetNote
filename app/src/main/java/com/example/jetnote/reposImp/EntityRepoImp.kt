package com.example.jetnote.reposImp

import com.example.jetnote.db.daos.EntityDao
import com.example.jetnote.db.entities.Entity
import com.example.jetnote.repos.EntityRepo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class EntityRepoImp @Inject constructor(
    private val dao: EntityDao
):EntityRepo {
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