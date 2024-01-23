package city.zouitel.links.mapper

import city.zouitel.links.mapper.base.Mapper
import city.zouitel.domain.model.NoteAndLink as OutNoteAndLink
import city.zouitel.links.model.NoteAndLink as InNoteAndLink

class NoteAndLinkMapper: Mapper.Base<InNoteAndLink, OutNoteAndLink> {
    override fun toView(data: OutNoteAndLink): InNoteAndLink = with(data) {
        InNoteAndLink(noteUid, linkId)
    }

    override fun toDomain(data: InNoteAndLink): OutNoteAndLink = with(data) {
        OutNoteAndLink(noteUid, linkId)
    }
}