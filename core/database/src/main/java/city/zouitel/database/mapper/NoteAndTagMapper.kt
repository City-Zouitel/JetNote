package city.zouitel.database.mapper

import city.zouitel.database.model.NoteAndTagEntity
import city.zouitel.database.model.NoteAndTagEntity as InNoteAndTag
import city.zouitel.repository.model.NoteAndTag as OutNoteAndTag

class NoteAndTagMapper {

    fun toRepo(notesAndTag: List<InNoteAndTag>) = notesAndTag.map { toRepo(it) }

    private fun toRepo(noteAndTag: InNoteAndTag) = OutNoteAndTag(
        noteUid = noteAndTag.noteUid,
        labelId = noteAndTag.labelId
    )

    fun fromRepo(noteAndTag: OutNoteAndTag) = InNoteAndTag(
        noteUid = noteAndTag.noteUid,
        labelId = noteAndTag.labelId
    )
}