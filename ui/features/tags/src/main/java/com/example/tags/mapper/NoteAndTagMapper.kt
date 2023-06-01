package com.example.tags.mapper

import com.example.domain.model.NoteAndTag as OutNoteAndTag
import com.example.tags.mapper.base.Mapper
import com.example.tags.model.NoteAndTag as InNoteAndTag

class NoteAndTagMapper: Mapper.Base<InNoteAndTag, OutNoteAndTag> {
    override fun toView(data: OutNoteAndTag): InNoteAndTag = with(data) {
        InNoteAndTag(noteUid, labelId)
    }

    override fun toDomain(data: InNoteAndTag): OutNoteAndTag = with(data){
        OutNoteAndTag(noteUid, labelId)
    }
}