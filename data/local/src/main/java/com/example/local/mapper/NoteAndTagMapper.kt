package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.NoteAndTagEntity as InNoteAndTag
import com.example.repository.model.NoteAndTag as OutNoteAndTag

class NoteAndTagMapper: Mapper.Base<InNoteAndTag, OutNoteAndTag> {
    override fun toLocal(data: OutNoteAndTag): InNoteAndTag = with(data){
        InNoteAndTag(noteUid, labelId)
    }

    override fun readOnly(data: InNoteAndTag): OutNoteAndTag = with(data){
        OutNoteAndTag(noteUid, labelId)
    }
}