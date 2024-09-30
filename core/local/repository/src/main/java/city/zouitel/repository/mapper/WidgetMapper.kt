package city.zouitel.repository.mapper

import city.zouitel.repository.model.Note as InNote
import city.zouitel.domain.model.Note as OutNote

class WidgetMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val taskMapper: TaskMapper,
    private val linkMapper: LinkMapper,
    private val audioMapper: AudioMapper,
    private val mediaMapper: MediaMapper
) {
    fun toDomain(notes: List<InNote>) = notes.map { toDomain(it) }

    fun toDomain(data: InNote): OutNote = with(data){
        OutNote(
            dataEntity = dataMapper.toDomain(dataEntity),
            tagEntities = tagEntities.map { tagMapper.toDomain(it) },
            taskEntities = taskEntities.map { taskMapper.toDomain(it) },
            linkEntities = linkEntities.map { linkMapper.toDomain(it) },
            audioEntities = audioEntities.map { audioMapper.toDomain(it) },
            mediaEntities = mediaEntities.map { mediaMapper.toDomain(it) }
        )
    }
}