package city.zouitel.links.mapper

import city.zouitel.links.mapper.base.Mapper
import city.zouitel.domain.model.NoteAndLink as OutNoteAndLink
import city.zouitel.links.model.NoteAndLink as InNoteAndLink

class NoteAndLinkMapper {

    fun fromDomain(notesAndLink: List<OutNoteAndLink>) = notesAndLink.map { fromDomain(it) }

    fun toDomain(notesAndLink: InNoteAndLink) = OutNoteAndLink(
        noteUid = notesAndLink.noteUid,
        linkId = notesAndLink.linkId
    )

    fun fromDomain(notesAndLink: OutNoteAndLink) = InNoteAndLink(
        noteUid = notesAndLink.noteUid,
        linkId = notesAndLink.linkId
    )
}