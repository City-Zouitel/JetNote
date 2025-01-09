package city.zouitel.screens.mapper

import city.zouitel.audios.mapper.AudioMapper
import city.zouitel.note.mapper.DataMapper
import city.zouitel.note.model.Note
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.domain.model.Note as OutNote

class NoteMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val audioMapper: AudioMapper
) {
    fun fromDomain(notes: List<OutNote>) = notes.map { fromDomain(it) }

    private fun fromDomain(data: OutNote): Note = with(data){
        Note(
            dataEntity = dataMapper.fromDomain(dataEntity),
            tagEntities = tagEntities.map { tagMapper.fromDomain(it) },
            audioEntities = audioEntities.map { audioMapper.fromDomain(it) }
        )
    }
}