package city.zouitel.recoder.mapper

import city.zouitel.recoder.mapper.base.Mapper
import city.zouitel.domain.model.NoteAndAudio as OutNoteAndAudio
import city.zouitel.recoder.model.NoteAndAudio as InNoteAndAudio

class NoteAndAudioMapper {

    fun fromDomain(notesAndAudio: List<OutNoteAndAudio>) = notesAndAudio.map { fromDomain(it) }

    fun toDomain(noteAndAudio: InNoteAndAudio) = OutNoteAndAudio(
        noteUid = noteAndAudio.noteUid,
        audioId = noteAndAudio.audioId
    )

    fun fromDomain(noteAndAudio: OutNoteAndAudio) = InNoteAndAudio(
        noteUid = noteAndAudio.noteUid,
        audioId = noteAndAudio.audioId
    )
}