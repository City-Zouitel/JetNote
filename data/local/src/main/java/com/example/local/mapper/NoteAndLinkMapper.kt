package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.NoteAndLinkEntity as InNoteAndLink
import com.example.repository.model.NoteAndLink as OutNoteAndLink

class NoteAndLinkMapper: Mapper.Base<InNoteAndLink, OutNoteAndLink> {
    override fun toLocal(data: OutNoteAndLink): InNoteAndLink = with(data){
        InNoteAndLink(noteUid, linkId)
    }

    override fun readOnly(data: InNoteAndLink): OutNoteAndLink = with(data){
        OutNoteAndLink(noteUid, linkId)
    }
}