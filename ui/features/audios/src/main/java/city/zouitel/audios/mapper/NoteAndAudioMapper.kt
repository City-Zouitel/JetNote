package city.zouitel.audios.mapper

import city.zouitel.audios.mapper.base.Mapper
import city.zouitel.domain.model.NoteAndAudio as OutNoteAndAudio
import city.zouitel.audios.model.NoteAndAudio as InNoteAndAudio

class NoteAndAudioMapper: Mapper.Base<InNoteAndAudio, OutNoteAndAudio> {
    override fun toView(data: OutNoteAndAudio): InNoteAndAudio = with(data) {
        InNoteAndAudio(noteUid, audioId)
    }

    override fun toDomain(data: InNoteAndAudio): OutNoteAndAudio = with(data) {
        OutNoteAndAudio(noteUid, audioId)
    }
}