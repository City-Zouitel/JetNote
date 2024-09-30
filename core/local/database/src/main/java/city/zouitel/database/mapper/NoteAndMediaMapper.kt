package city.zouitel.database.mapper

import city.zouitel.repository.model.NoteAndMedia
import city.zouitel.database.model.NoteAndMediaEntity as InNoteAndMedia
import city.zouitel.repository.model.NoteAndMedia as OutNoteAndMedia

class NoteAndMediaMapper {

    fun toRepo(notesAndMedia: List<InNoteAndMedia>) = notesAndMedia.map { toRepo(it) }

    fun toRepo(noteAndMedia: InNoteAndMedia) = OutNoteAndMedia(
        noteUid = noteAndMedia.noteUid,
        mediaId = noteAndMedia.mediaId
    )

    fun fromRepo(noteAndMedia: OutNoteAndMedia) = InNoteAndMedia(
        noteUid = noteAndMedia.noteUid,
        mediaId = noteAndMedia.mediaId
    )
}