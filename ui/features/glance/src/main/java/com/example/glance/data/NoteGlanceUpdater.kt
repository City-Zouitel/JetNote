package com.example.glance.data

import com.example.glance.mapper.NoteMapper
import com.example.glance.model.GlanceNote
import com.example.local.daos.EntityDao
import com.example.local.daos.NotesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteGlanceUpdater(
    private val loadAllNotesDao: EntityDao,
    private val mapper:NoteMapper
) {
    fun loadNote(): Flow<List<GlanceNote>> =
        loadAllNotesDao.allEntitiesOrderedById().map {
            mapper.toView(it)
        }

}