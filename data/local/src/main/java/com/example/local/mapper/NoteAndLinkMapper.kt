package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.NoteAndLinkEntity
import com.example.repository.model.NoteAndLink

class NoteAndLinkMapper: Mapper.Base<NoteAndLinkEntity, NoteAndLink> {
    override fun toLocal(data: NoteAndLink): NoteAndLinkEntity = with(data){
        NoteAndLinkEntity(noteUid, linkId)
    }

    override fun readOnly(data: NoteAndLinkEntity): NoteAndLink = with(data){
        NoteAndLink(noteUid, linkId)
    }
}