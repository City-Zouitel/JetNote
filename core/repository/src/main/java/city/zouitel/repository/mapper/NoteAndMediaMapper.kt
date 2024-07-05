package city.zouitel.repository.mapper

import city.zouitel.repository.model.NoteAndMedia as InNoteAndMedia
import city.zouitel.domain.model.NoteAndMedia as OutNoteAndMedia

class NoteAndMediaMapper {

    fun toDomain(notesAndMedia: List<InNoteAndMedia>) = notesAndMedia.map { toDomain(it) }

    fun toDomain(noteAndMedia: InNoteAndMedia) = OutNoteAndMedia(
        noteUid = noteAndMedia.noteUid,
        mediaId = noteAndMedia.mediaId
    )

    fun fromDomain(noteAndMedia: OutNoteAndMedia) = InNoteAndMedia(
        noteUid = noteAndMedia.noteUid,
        mediaId = noteAndMedia.mediaId
    )
}