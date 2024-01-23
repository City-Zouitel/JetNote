package city.zouitel.note.mapper

import city.zouitel.links.mapper.LinkMapper
import city.zouitel.domain.model.Note as OutNote
import city.zouitel.note.mapper.base.Mapper
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.tasks.mapper.TaskMapper
import city.zouitel.note.model.Note as InNote

class NoteMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val taskMapper: TaskMapper,
    private val linkMapper: LinkMapper
): Mapper.ReadOnly<InNote, OutNote> {
    override fun toView(data: OutNote): InNote = with(data){
        InNote(
            dataEntity = dataMapper.toView(dataEntity),
            tagEntities = tagEntities.map { tagMapper.toView(it) },
            taskEntities = taskEntities.map { taskMapper.toView(it) },
            linkEntities = linkEntities.map { linkMapper.toView(it) }
        )
    }
}