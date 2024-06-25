package city.zouitel.repository.mapper

import city.zouitel.domain.model.NoteAndAudio as OutNoteAndAudio
import city.zouitel.repository.model.NoteAndAudio as InNoteAndAudio

class NoteAndAudioMapper {

    fun toDomain(notesAndAudio: List<InNoteAndAudio>) = notesAndAudio.map { toDomain(it) }

    private fun toDomain(noteAndAudio: InNoteAndAudio) = OutNoteAndAudio(
        noteUid = noteAndAudio.noteUid,
        audioId = noteAndAudio.audioId
    )

    fun fromDomain(noteAndAudio: OutNoteAndAudio) = InNoteAndAudio(
        noteUid = noteAndAudio.noteUid,
        audioId = noteAndAudio.audioId
    )
}