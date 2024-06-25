package city.zouitel.database.mapper

import city.zouitel.repository.model.NoteAndLink
import city.zouitel.database.model.NoteAndLinkEntity as InNoteAndLink
import city.zouitel.repository.model.NoteAndLink as OutNoteAndLink

class NoteAndLinkMapper {

    fun toRepo(notesAndLink: List<InNoteAndLink>) = notesAndLink.map { toRepo(it) }

    private fun toRepo(notesAndLink: InNoteAndLink) = OutNoteAndLink(
        noteUid = notesAndLink.noteUid,
        linkId = notesAndLink.linkId
    )

    fun fromRepo(notesAndLink: OutNoteAndLink) = InNoteAndLink(
        noteUid = notesAndLink.noteUid,
        linkId = notesAndLink.linkId
    )
}