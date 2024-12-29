package city.zouitel.screens.mapper

import city.zouitel.audios.mapper.AudioMapper
import city.zouitel.links.mapper.LinkMapper
import city.zouitel.note.mapper.DataMapper
import city.zouitel.note.model.Note
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.domain.model.Note as OutNote

class NoteMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val linkMapper: LinkMapper,
    private val audioMapper: AudioMapper
) {
    fun fromDomain(notes: List<OutNote>) = notes.map { fromDomain(it) }

    private fun fromDomain(data: OutNote): Note = with(data){
        Note(
            dataEntity = dataMapper.fromDomain(dataEntity),
            tagEntities = tagEntities.map { tagMapper.fromDomain(it) },
            linkEntities = linkEntities.map { linkMapper.fromDomain(it) },
            audioEntities = audioEntities.map { audioMapper.fromDomain(it) }
        )
    }
}