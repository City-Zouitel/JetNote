package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.NoteAndTagEntity
import com.example.repository.model.NoteAndTag

class NoteAndTagMapper: Mapper.Base<NoteAndTagEntity, NoteAndTag> {
    override fun toLocal(data: NoteAndTag): NoteAndTagEntity = with(data){
        NoteAndTagEntity(noteUid, labelId)
    }

    override fun readOnly(data: NoteAndTagEntity): NoteAndTag = with(data){
        NoteAndTag(noteUid, labelId)
    }
}