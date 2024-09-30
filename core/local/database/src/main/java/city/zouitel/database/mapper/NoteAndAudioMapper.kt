package city.zouitel.database.mapper

import city.zouitel.repository.model.NoteAndAudio as OutNoteAndAudio
import city.zouitel.database.model.NoteAndAudioEntity as InNoteAndAudio

class NoteAndAudioMapper {

    fun toRepo(notesAndAudio: List<InNoteAndAudio>) = notesAndAudio.map { toRepo(it) }

    private fun toRepo(noteAndAudio: InNoteAndAudio) = OutNoteAndAudio(
        noteUid = noteAndAudio.noteUid,
        audioId = noteAndAudio.audioId
    )

    fun fromRepo(noteAndAudio: OutNoteAndAudio) = InNoteAndAudio(
        noteUid = noteAndAudio.noteUid,
        audioId = noteAndAudio.audioId
    )
}