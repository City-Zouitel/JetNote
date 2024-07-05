package city.zouitel.screens.mapper

import city.zouitel.audios.mapper.AudioMapper
import city.zouitel.links.mapper.LinkMapper
import city.zouitel.media.mapper.MediaMapper
import city.zouitel.note.mapper.DataMapper
import city.zouitel.domain.model.Note as OutNote
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.tasks.mapper.TaskMapper
import city.zouitel.note.model.Note as InNote

class NoteMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val taskMapper: TaskMapper,
    private val linkMapper: LinkMapper,
    private val audioMapper: AudioMapper,
    private val mediaMapper: MediaMapper
) {
    fun fromDomain(notes: List<OutNote>) = notes.map { fromDomain(it) }

    private fun fromDomain(data: OutNote): InNote = with(data){
        InNote(
            dataEntity = dataMapper.fromDomain(dataEntity),
            tagEntities = tagEntities.map { tagMapper.fromDomain(it) },
            taskEntities = taskEntities.map { taskMapper.fromDomain(it) },
            linkEntities = linkEntities.map { linkMapper.fromDomain(it) },
            audioEntities = audioEntities.map { audioMapper.fromDomain(it) },
            mediaEntities = mediaEntities.map { mediaMapper.fromDomain(it) }
        )
    }
}