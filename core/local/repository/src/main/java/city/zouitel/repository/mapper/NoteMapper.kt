package city.zouitel.repository.mapper

import city.zouitel.repository.model.Note
import city.zouitel.domain.model.Note as OutNote

class NoteMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val audioMapper: AudioMapper,
) {
    fun toDomain(notes: List<Note>) = notes.map { toDomain(it) }

    fun toDomain(data: Note): OutNote = with(data){
        OutNote(
            dataEntity = dataMapper.toDomain(dataEntity),
            tagEntities = tagEntities.map { tagMapper.toDomain(it) },
            audioEntities = audioEntities.map { audioMapper.toDomain(it) }
        )
    }
}