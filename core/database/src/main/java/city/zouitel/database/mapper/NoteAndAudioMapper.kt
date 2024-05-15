package city.zouitel.database.mapper

import city.zouitel.database.mapper.base.Mapper
import city.zouitel.repository.model.NoteAndAudio as OutNoteAndAudio
import city.zouitel.database.model.NoteAndAudioEntity as InNoteAndAudio

class NoteAndAudioMapper: Mapper.Base<InNoteAndAudio, OutNoteAndAudio> {
    override fun toLocal(data: OutNoteAndAudio): InNoteAndAudio = with(data) {
        InNoteAndAudio(noteUid, audioId)
    }

    override fun readOnly(data: InNoteAndAudio): OutNoteAndAudio = with(data) {
        OutNoteAndAudio(noteUid, audioId)
    }
}