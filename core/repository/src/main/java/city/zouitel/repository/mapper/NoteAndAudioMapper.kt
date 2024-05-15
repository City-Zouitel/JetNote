package city.zouitel.repository.mapper

import city.zouitel.repository.mapper.base.Mapper
import city.zouitel.domain.model.NoteAndAudio as OutNoteAndAudio
import city.zouitel.repository.model.NoteAndAudio as InNoteAndAudio

class NoteAndAudioMapper: Mapper.Base<InNoteAndAudio, OutNoteAndAudio> {
    override fun toRepository(data: OutNoteAndAudio): InNoteAndAudio = with(data) {
        InNoteAndAudio(noteUid, audioId)
    }

    override fun toDomain(data: InNoteAndAudio): OutNoteAndAudio = with(data) {
        OutNoteAndAudio(noteUid, audioId)
    }
}