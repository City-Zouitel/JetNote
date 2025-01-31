package city.zouitel.tags.mapper

import city.zouitel.tags.model.NoteAndTag
import city.zouitel.domain.model.NoteAndTag as OutNoteAndTag

class NoteAndTagMapper {

    fun fromDomain(notesAndTag: List<OutNoteAndTag>) = notesAndTag.map { fromDomain(it) }

    fun toDomain(noteAndTag: NoteAndTag) = OutNoteAndTag(
        uid = noteAndTag.uid,
        id = noteAndTag.id
    )

    private fun fromDomain(noteAndTag: OutNoteAndTag) = NoteAndTag(
        uid = noteAndTag.uid,
        id = noteAndTag.id
    )
}