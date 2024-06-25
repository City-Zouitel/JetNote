package city.zouitel.tags.mapper

import city.zouitel.domain.model.NoteAndTag as OutNoteAndTag
import city.zouitel.tags.mapper.base.Mapper
import city.zouitel.tags.model.NoteAndTag as InNoteAndTag

class NoteAndTagMapper {

    fun fromDomain(notesAndTag: List<OutNoteAndTag>) = notesAndTag.map { fromDomain(it) }

    fun toDomain(noteAndTag: InNoteAndTag) = OutNoteAndTag(
        noteUid = noteAndTag.noteUid,
        labelId = noteAndTag.labelId
    )

    fun fromDomain(noteAndTag: OutNoteAndTag) = InNoteAndTag(
        noteUid = noteAndTag.noteUid,
        labelId = noteAndTag.labelId
    )
}