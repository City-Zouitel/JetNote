package city.zouitel.media.mapper

import city.zouitel.media.model.NoteAndMedia as InNoteAndMedia
import city.zouitel.domain.model.NoteAndMedia as OutNoteAndMedia

class NoteAndMediaMapper {

    fun fromDomain(notesAndMedia: List<OutNoteAndMedia>) = notesAndMedia.map { fromDomain(it) }

    fun toDomain(noteAndMedia: InNoteAndMedia) = OutNoteAndMedia(
        noteUid = noteAndMedia.noteUid,
        mediaId = noteAndMedia.mediaId
    )

    fun fromDomain(noteAndMedia: OutNoteAndMedia) = InNoteAndMedia(
        noteUid = noteAndMedia.noteUid,
        mediaId = noteAndMedia.mediaId
    )
}