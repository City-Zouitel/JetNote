package com.example.repository.mapper

import com.example.repository.mapper.base.Mapper
import com.example.repository.model.NoteAndLink as InNoteAndLink
import com.example.domain.model.NoteAndLink as OutNoteAndLink

class NoteAndLinkMapper: Mapper.Base<InNoteAndLink, OutNoteAndLink> {
    override fun toRepository(data: OutNoteAndLink): InNoteAndLink = with(data){
        InNoteAndLink(noteUid, linkId)
    }

    override fun readOnly(data: InNoteAndLink): OutNoteAndLink = with(data){
        OutNoteAndLink(noteUid, linkId)
    }
}