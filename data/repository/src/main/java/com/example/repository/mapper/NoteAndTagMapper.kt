package com.example.repository.mapper

import com.example.repository.mapper.base.Mapper
import com.example.repository.model.NoteAndTag as InNoteAndTag
import com.example.domain.model.NoteAndTag as OutNoteAndTag

class NoteAndTagMapper: Mapper.Base<InNoteAndTag, OutNoteAndTag> {
    override fun toRepository(data: OutNoteAndTag): InNoteAndTag = with(data){
        InNoteAndTag(noteUid, labelId)
    }

    override fun readOnly(data: InNoteAndTag): OutNoteAndTag = with(data){
        OutNoteAndTag(noteUid, labelId)
    }
}