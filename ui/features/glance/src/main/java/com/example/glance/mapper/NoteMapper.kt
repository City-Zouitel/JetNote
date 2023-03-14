package com.example.glance.mapper

import com.example.glance.model.GlanceNote
import com.example.local.model.Entity
import com.example.local.model.Note

class NoteMapper {

    fun toView(localNotes: List<Entity>):List<GlanceNote> {
        return localNotes.map { toView(it) }
    }
    
    private fun toView(localNote: Entity): GlanceNote {
        return GlanceNote(id = localNote.note.uid, title = localNote.note.title)
    }
}