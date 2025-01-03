package city.zouitel.database.mapper

import city.zouitel.database.model.relational.NoteEntity as InNote
import city.zouitel.repository.model.Note as OutNote

class NoteMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val linkMapper: LinkMapper,
    private val audioMapper: AudioMapper
) {
    fun toRepo(notes: List<InNote>) = notes.map { toRepo(it) }

    fun toRepo(data: InNote): OutNote = with(data){
        OutNote(
            dataEntity = dataMapper.toRepo(dataEntity),
            tagEntities = tagEntities.map { tagMapper.toRepo(it) },
            linkEntities = linkEntities.map { linkMapper.toRepo(it) },
            audioEntities = audioEntities.map { audioMapper.toRepo(it) }
        )
    }
}