package city.zouitel.repository.mapper

import city.zouitel.repository.mapper.base.Mapper
import city.zouitel.repository.model.NoteAndLink as InNoteAndLink
import city.zouitel.domain.model.NoteAndLink as OutNoteAndLink

class NoteAndLinkMapper: Mapper.Base<InNoteAndLink, OutNoteAndLink> {
    override fun toRepository(data: OutNoteAndLink): InNoteAndLink = with(data){
        InNoteAndLink(noteUid, linkId)
    }

    override fun toDomain(data: InNoteAndLink): OutNoteAndLink = with(data){
        OutNoteAndLink(noteUid, linkId)
    }
}