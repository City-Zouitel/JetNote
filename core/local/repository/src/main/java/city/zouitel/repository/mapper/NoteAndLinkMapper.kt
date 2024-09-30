package city.zouitel.repository.mapper

import city.zouitel.repository.model.NoteAndLink as InNoteAndLink
import city.zouitel.domain.model.NoteAndLink as OutNoteAndLink

class NoteAndLinkMapper {

    fun toDomain(notesAndLink: List<InNoteAndLink>) = notesAndLink.map { toDomain(it) }

    private fun toDomain(notesAndLink: InNoteAndLink) = OutNoteAndLink(
        noteUid = notesAndLink.noteUid,
        linkId = notesAndLink.linkId
    )

    fun fromDomain(notesAndLink: OutNoteAndLink) = InNoteAndLink(
        noteUid = notesAndLink.noteUid,
        linkId = notesAndLink.linkId
    )
}