package city.zouitel.repository.mapper

import city.zouitel.repository.model.NoteAndTag as InNoteAndTag
import city.zouitel.domain.model.NoteAndTag as OutNoteAndTag

class NoteAndTagMapper {

    fun toDomain(notesAndTag: List<InNoteAndTag>) = notesAndTag.map { toDomain(it) }

    private fun toDomain(noteAndTag: InNoteAndTag) = OutNoteAndTag(
        noteUid = noteAndTag.noteUid,
        labelId = noteAndTag.labelId
    )

    fun fromDomain(noteAndTag: OutNoteAndTag) = InNoteAndTag(
        noteUid = noteAndTag.noteUid,
        labelId = noteAndTag.labelId
    )
}