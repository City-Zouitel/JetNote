package city.zouitel.repository.mapper

import city.zouitel.domain.model.Note as OutNote
import city.zouitel.repository.model.Note as InNote

class NoteMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val linkMapper: LinkMapper,
    private val audioMapper: AudioMapper,
    private val mediaMapper: MediaMapper
) {
    fun toDomain(notes: List<InNote>) = notes.map { toDomain(it) }

    fun toDomain(data: InNote): OutNote = with(data){
        OutNote(
            dataEntity = dataMapper.toDomain(dataEntity),
            tagEntities = tagEntities.map { tagMapper.toDomain(it) },
            linkEntities = linkEntities.map { linkMapper.toDomain(it) },
            audioEntities = audioEntities.map { audioMapper.toDomain(it) },
            mediaEntities = mediaEntities.map { mediaMapper.toDomain(it) }
        )
    }
}