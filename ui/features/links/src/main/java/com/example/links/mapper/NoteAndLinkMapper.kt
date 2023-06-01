package com.example.links.mapper

import com.example.links.mapper.base.Mapper
import com.example.domain.model.NoteAndLink as OutNoteAndLink
import com.example.links.model.NoteAndLink as InNoteAndLink

class NoteAndLinkMapper: Mapper.Base<InNoteAndLink, OutNoteAndLink> {
    override fun toView(data: OutNoteAndLink): InNoteAndLink = with(data) {
        InNoteAndLink(noteUid, linkId)
    }

    override fun toDomain(data: InNoteAndLink): OutNoteAndLink = with(data) {
        OutNoteAndLink(noteUid, linkId)
    }
}