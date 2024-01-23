package city.zouitel.database.mapper

import city.zouitel.database.mapper.base.Mapper
import city.zouitel.database.model.NoteAndLinkEntity as InNoteAndLink
import city.zouitel.repository.model.NoteAndLink as OutNoteAndLink

class NoteAndLinkMapper: Mapper.Base<InNoteAndLink, OutNoteAndLink> {
    override fun toLocal(data: OutNoteAndLink): InNoteAndLink = with(data){
        InNoteAndLink(noteUid, linkId)
    }

    override fun readOnly(data: InNoteAndLink): OutNoteAndLink = with(data){
        OutNoteAndLink(noteUid, linkId)
    }
}